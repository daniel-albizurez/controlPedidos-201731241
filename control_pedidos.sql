CREATE DATABASE IF NOT EXISTS control_pedidos;

USE control_pedidos;

CREATE TABLE IF NOT EXISTS cliente(
    nit VARCHAR(13),
    nombre VARCHAR(50) NOT NULL,
    telefono VARCHAR(8) NOT NULL,
    dpi VARCHAR(13) DEFAULT 'N/A',
    direccion VARCHAR(100) DEFAULT 'N/A',
    email VARCHAR(50) DEFAULT 'N/A',
    credito DOUBLE DEFAULT 0.0,
    PRIMARY KEY (nit)
);

CREATE TABLE IF NOT EXISTS tienda(
    codigo VARCHAR(15),
    nombre VARCHAR(50) NOT NULL,
    direccion VARCHAR(100) NOT NULL,
    telefono1 VARCHAR(8) NOT NULL,
    telefono2 VARCHAR(8) DEFAULT 'N/A',
    email VARCHAR(50) DEFAULT 'N/A',
    horario VARCHAR(11) DEFAULT '10:00-18:00',
    PRIMARY KEY (codigo)
);

CREATE TABLE IF NOT EXISTS producto(
    codigo VARCHAR(15),
    nombre VARCHAR(50) NOT NULL,
    fabricante VARCHAR(50) NOT NULL,
    precio DOUBLE NOT NULL,
    descripcion VARCHAR(100) DEFAULT 'N/A',
    garantia INT DEFAULT 12,
    PRIMARY KEY (codigo)
);

CREATE TABLE IF NOT EXISTS ubicacion(
    producto VARCHAR(15),
    tienda VARCHAR(15),
    cantidad INT DEFAULT 1,
    CONSTRAINT PK_ubicacion PRIMARY KEY(producto, tienda),
    CONSTRAINT FK_ubicacionProducto FOREIGN KEY(producto) REFERENCES producto(codigo)
    ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT FK_ubicacionTienda FOREIGN KEY(tienda) REFERENCES tienda(codigo)
    ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE IF NOT EXISTS tiempo (
    tienda1 VARCHAR(15),
    tienda2 VARCHAR(15),
    dias INT DEFAULT 8,
    CONSTRAINT PK_tiempo PRIMARY KEY (tienda1, tienda2),
    CONSTRAINT FK_tiempoTienda1 FOREIGN  KEY (tienda1)
    REFERENCES tienda(codigo),
    CONSTRAINT FK_tiempoTienda2 FOREIGN  KEY (tienda2)
    REFERENCES tienda(codigo)
);

CREATE TABLE IF NOT EXISTS venta(
    codigo INT,
    tienda VARCHAR(15),
    cliente VARCHAR(13) DEFAULT 'CF',
    fecha DATE,
    total DOUBLE,
    PRIMARY KEY  (codigo),
    CONSTRAINT FK_ventaTienda FOREIGN KEY (tienda)
    REFERENCES tienda(codigo),
    CONSTRAINT FK_ventaCliente FOREIGN KEY (cliente)
    REFERENCES cliente(nit)
);

CREATE TABLE IF NOT EXISTS detalle_venta(
    venta INT,
    producto VARCHAR(15),
    cantidad INT,
    CONSTRAINT PK_detalleVenta PRIMARY KEY (venta, producto),
    CONSTRAINT FK_detalleVenta FOREIGN KEY (venta) 
    REFERENCES venta(codigo) ON DELETE CASCADE,
    CONSTRAINT FK_detalleVentaProducto FOREIGN KEY (producto)
    REFERENCES producto(codigo)
);

CREATE TABLE IF NOT EXISTS pedido(
    codigo INT,
    tienda_origen VARCHAR(15),
    tienda_destino VARCHAR(15),
    fecha_pedido DATE,
    cliente VARCHAR(13) DEFAULT 'CF',
    anticipo DOUBLE,
    total DOUBLE,
    fecha_cancelacion DATE DEFAULT '2020-12-31',
    fecha_en_tienda DATE DEFAULT '2020-12-31',
    estado VARCHAR(10) DEFAULT 'En ruta',
    PRIMARY KEY(codigo),
    CONSTRAINT FK_pedidoTiendaOrigen FOREIGN KEY (tienda_origen)
    REFERENCES tienda(codigo),
    CONSTRAINT FK_pedidoTiendaDestino FOREIGN KEY (tienda_destino)
    REFERENCES tienda(codigo),
    CONSTRAINT FK_pedidoCliente FOREIGN KEY (cliente)
    REFERENCES cliente(nit)
);

CREATE TABLE IF NOT EXISTS detalle_pedido(
    pedido INT,
    producto VARCHAR(15),
    cantidad INT,
    CONSTRAINT PK_detallePedido PRIMARY KEY (pedido,producto),
    CONSTRAINT FK_detallePedido FOREIGN KEY (pedido)
    REFERENCES pedido(codigo),
    CONSTRAINT FK_detallePedidoProducto FOREIGN KEY (producto)
    REFERENCES producto(codigo)
);

 CREATE VIEW venta_detallada AS
 SELECT v.codigo AS venta, c.nit, c.nombre AS cliente, v.total, p.nombre AS producto, p.precio , d.cantidad FROM
 venta v INNER JOIN detalle_venta d ON v.codigo = d.venta
 INNER JOIN producto p ON p.codigo = d.producto
 INNER JOIN cliente c ON c.nit = v.cliente;

 CREATE VIEW pedido_detallado AS
 SELECT pd.codigo AS pedido, c.nit, c.nombre AS cliente , pd.total, p.nombre AS producto, p.precio , d.cantidad FROM
 pedido pd INNER JOIN detalle_pedido d ON pd.codigo = d.pedido
 INNER JOIN producto p ON p.codigo = d.producto
 INNER JOIN cliente c ON c.nit = pd.cliente
 WHERE pd.estado = 'Pagado';

CREATE VIEW pedidos_en_ruta AS
SELECT pd.codigo AS pedido, DATE_ADD(pd.fecha_pedido, INTERVAL t.dias DAY) AS fecha_estimada
, c.nit, c.nombre AS cliente, pd.total,
 p.nombre AS producto, d.cantidad FROM
pedido pd 
 INNER JOIN detalle_pedido d ON pd.codigo = d.pedido
 INNER JOIN producto p ON p.codigo = d.producto
 INNER JOIN cliente c ON c.nit = pd.cliente
 INNER JOIN tiempo t ON t.tienda1 = pd.tienda_origen AND t.tienda2 = pd.tienda_destino
 OR t.tienda2 = pd.tienda_origen AND t.tienda1 = pd.tienda_destino 
 WHERE pd.estado = 'En ruta' OR pd.estado = "Atrasado"
 ;

CREATE VIEW mas_vendidos AS
SELECT COUNT(d.producto) AS cantidad, p.nombre AS nombre_producto, v.fecha
FROM venta v
INNER JOIN detalle_venta d ON v.codigo = d.venta
INNER JOIN producto p ON p.codigo = d.producto
GROUP BY producto
ORDER BY cantidad
;

CREATE VIEW mas_vendidos_tienda AS
SELECT COUNT(d.producto) AS cantidad, p.nombre AS nombre_producto, v.fecha, v.tienda, t.nombre
FROM venta v
INNER JOIN detalle_venta d ON v.codigo = d.venta
INNER JOIN producto p ON p.codigo = d.producto
INNER JOIN tienda t ON v.tienda = t.codigo
GROUP BY producto,tienda
ORDER BY cantidad
;

CREATE VIEW no_vendidos AS
SELECT p.codigo, p.nombre AS nombre_producto, t.codigo AS codigo_tienda, t.nombre, u.cantidad 
FROM producto p 
INNER JOIN ubicacion u ON u.producto = p.codigo
INNER JOIN tienda t ON u.tienda = t.codigo
LEFT JOIN detalle_venta d ON d.producto = p.codigo
WHERE d.producto IS NULL
;