-- Sample dataset for the Pack-it-flawlessly demo.
-- Safe to run more than once: package references and package/SKU pairs are checked first.

INSERT INTO packages (name, reference, description, status, created_at, updated_at)
SELECT 'Pedido de cocina y hogar', 'PF-2026-001', 'Set de productos para cocina; orden recién recibida.', 'RECEIVED', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
WHERE NOT EXISTS (SELECT 1 FROM packages WHERE reference = 'PF-2026-001');

INSERT INTO packages (name, reference, description, status, created_at, updated_at)
SELECT 'Accesorios de escritorio', 'PF-2026-002', 'Accesorios de oficina para despacho nacional.', 'RECEIVED', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
WHERE NOT EXISTS (SELECT 1 FROM packages WHERE reference = 'PF-2026-002');

INSERT INTO packages (name, reference, description, status, created_at, updated_at)
SELECT 'Kit de café de especialidad', 'PF-2026-003', 'Pedido en preparación en el centro de distribución.', 'PREPARING', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
WHERE NOT EXISTS (SELECT 1 FROM packages WHERE reference = 'PF-2026-003');

INSERT INTO packages (name, reference, description, status, created_at, updated_at)
SELECT 'Pedido de cuidado personal', 'PF-2026-004', 'Productos agrupados y pendientes de control final.', 'PREPARING', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
WHERE NOT EXISTS (SELECT 1 FROM packages WHERE reference = 'PF-2026-004');

INSERT INTO packages (name, reference, description, status, created_at, updated_at)
SELECT 'Pack de lectura', 'PF-2026-005', 'Empaque listo; esperando retiro del transportista.', 'READY', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
WHERE NOT EXISTS (SELECT 1 FROM packages WHERE reference = 'PF-2026-005');

INSERT INTO packages (name, reference, description, status, created_at, updated_at)
SELECT 'Set de organización', 'PF-2026-006', 'Pedido embalado y etiquetado para despacho.', 'READY', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
WHERE NOT EXISTS (SELECT 1 FROM packages WHERE reference = 'PF-2026-006');

INSERT INTO packages (name, reference, description, status, created_at, updated_at)
SELECT 'Pack de entrenamiento', 'PF-2026-007', 'En ruta al domicilio del destinatario.', 'IN_TRANSIT', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
WHERE NOT EXISTS (SELECT 1 FROM packages WHERE reference = 'PF-2026-007');

INSERT INTO packages (name, reference, description, status, created_at, updated_at)
SELECT 'Pedido de accesorios de viaje', 'PF-2026-008', 'Entrega completada y confirmada.', 'DELIVERED', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
WHERE NOT EXISTS (SELECT 1 FROM packages WHERE reference = 'PF-2026-008');

INSERT INTO packages (name, reference, description, status, created_at, updated_at)
SELECT 'Pedido cancelado', 'PF-2026-009', 'Registro archivado para conservar trazabilidad.', 'ARCHIVED', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
WHERE NOT EXISTS (SELECT 1 FROM packages WHERE reference = 'PF-2026-009');

INSERT INTO package_items (sku, product_name, quantity, package_id)
SELECT 'KIT-TAZA-01', 'Tazón cerámico azul', 2, p.id FROM packages p
WHERE p.reference = 'PF-2026-001' AND NOT EXISTS (SELECT 1 FROM package_items i WHERE i.package_id = p.id AND i.sku = 'KIT-TAZA-01');
INSERT INTO package_items (sku, product_name, quantity, package_id)
SELECT 'KIT-MATE-02', 'Set de mate de acero', 1, p.id FROM packages p
WHERE p.reference = 'PF-2026-001' AND NOT EXISTS (SELECT 1 FROM package_items i WHERE i.package_id = p.id AND i.sku = 'KIT-MATE-02');

INSERT INTO package_items (sku, product_name, quantity, package_id)
SELECT 'DESK-LAMP-01', 'Lámpara LED de escritorio', 1, p.id FROM packages p
WHERE p.reference = 'PF-2026-002' AND NOT EXISTS (SELECT 1 FROM package_items i WHERE i.package_id = p.id AND i.sku = 'DESK-LAMP-01');
INSERT INTO package_items (sku, product_name, quantity, package_id)
SELECT 'DESK-ORG-02', 'Organizador modular', 2, p.id FROM packages p
WHERE p.reference = 'PF-2026-002' AND NOT EXISTS (SELECT 1 FROM package_items i WHERE i.package_id = p.id AND i.sku = 'DESK-ORG-02');

INSERT INTO package_items (sku, product_name, quantity, package_id)
SELECT 'COFFEE-GR-01', 'Café de grano tueste medio', 2, p.id FROM packages p
WHERE p.reference = 'PF-2026-003' AND NOT EXISTS (SELECT 1 FROM package_items i WHERE i.package_id = p.id AND i.sku = 'COFFEE-GR-01');
INSERT INTO package_items (sku, product_name, quantity, package_id)
SELECT 'COFFEE-FLT-02', 'Filtros reutilizables', 1, p.id FROM packages p
WHERE p.reference = 'PF-2026-003' AND NOT EXISTS (SELECT 1 FROM package_items i WHERE i.package_id = p.id AND i.sku = 'COFFEE-FLT-02');

INSERT INTO package_items (sku, product_name, quantity, package_id)
SELECT 'CARE-SKIN-01', 'Crema hidratante diaria', 1, p.id FROM packages p
WHERE p.reference = 'PF-2026-004' AND NOT EXISTS (SELECT 1 FROM package_items i WHERE i.package_id = p.id AND i.sku = 'CARE-SKIN-01');
INSERT INTO package_items (sku, product_name, quantity, package_id)
SELECT 'CARE-SOAP-02', 'Jabón vegetal', 3, p.id FROM packages p
WHERE p.reference = 'PF-2026-004' AND NOT EXISTS (SELECT 1 FROM package_items i WHERE i.package_id = p.id AND i.sku = 'CARE-SOAP-02');

INSERT INTO package_items (sku, product_name, quantity, package_id)
SELECT 'BOOK-DES-01', 'Diseño de sistemas cotidianos', 1, p.id FROM packages p
WHERE p.reference = 'PF-2026-005' AND NOT EXISTS (SELECT 1 FROM package_items i WHERE i.package_id = p.id AND i.sku = 'BOOK-DES-01');
INSERT INTO package_items (sku, product_name, quantity, package_id)
SELECT 'BOOK-UX-02', 'Introducción al diseño UX', 1, p.id FROM packages p
WHERE p.reference = 'PF-2026-005' AND NOT EXISTS (SELECT 1 FROM package_items i WHERE i.package_id = p.id AND i.sku = 'BOOK-UX-02');

INSERT INTO package_items (sku, product_name, quantity, package_id)
SELECT 'ORG-BOX-01', 'Caja organizadora plegable', 3, p.id FROM packages p
WHERE p.reference = 'PF-2026-006' AND NOT EXISTS (SELECT 1 FROM package_items i WHERE i.package_id = p.id AND i.sku = 'ORG-BOX-01');
INSERT INTO package_items (sku, product_name, quantity, package_id)
SELECT 'ORG-LBL-02', 'Set de etiquetas adhesivas', 2, p.id FROM packages p
WHERE p.reference = 'PF-2026-006' AND NOT EXISTS (SELECT 1 FROM package_items i WHERE i.package_id = p.id AND i.sku = 'ORG-LBL-02');

INSERT INTO package_items (sku, product_name, quantity, package_id)
SELECT 'FIT-BAND-01', 'Bandas elásticas de resistencia', 1, p.id FROM packages p
WHERE p.reference = 'PF-2026-007' AND NOT EXISTS (SELECT 1 FROM package_items i WHERE i.package_id = p.id AND i.sku = 'FIT-BAND-01');
INSERT INTO package_items (sku, product_name, quantity, package_id)
SELECT 'FIT-BOT-02', 'Botella deportiva térmica', 1, p.id FROM packages p
WHERE p.reference = 'PF-2026-007' AND NOT EXISTS (SELECT 1 FROM package_items i WHERE i.package_id = p.id AND i.sku = 'FIT-BOT-02');

INSERT INTO package_items (sku, product_name, quantity, package_id)
SELECT 'TRAVEL-ORG-01', 'Organizador de equipaje', 2, p.id FROM packages p
WHERE p.reference = 'PF-2026-008' AND NOT EXISTS (SELECT 1 FROM package_items i WHERE i.package_id = p.id AND i.sku = 'TRAVEL-ORG-01');
INSERT INTO package_items (sku, product_name, quantity, package_id)
SELECT 'TRAVEL-LOCK-02', 'Candado TSA', 2, p.id FROM packages p
WHERE p.reference = 'PF-2026-008' AND NOT EXISTS (SELECT 1 FROM package_items i WHERE i.package_id = p.id AND i.sku = 'TRAVEL-LOCK-02');

INSERT INTO package_items (sku, product_name, quantity, package_id)
SELECT 'CANCEL-MUG-01', 'Taza térmica', 1, p.id FROM packages p
WHERE p.reference = 'PF-2026-009' AND NOT EXISTS (SELECT 1 FROM package_items i WHERE i.package_id = p.id AND i.sku = 'CANCEL-MUG-01');
