CREATE DATABASE gestaoEstoquePavimor;
USE gestaoEstoquePavimor;

CREATE TABLE produtos (
idProduto INT PRIMARY KEY AUTO_INCREMENT,
nomeProduto VARCHAR(50) NOT NULL,
unidade VARCHAR(50),
localizacao VARCHAR(50) NOT NULL,
quantidade INT NOT NULL,
custoUnitario DECIMAL(10,2) NOT NULL,
dataEntrada DATETIME DEFAULT CURRENT_TIMESTAMP,
dataSaida DATETIME DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP
);

-- Inserção de dados para fábrica de blocos
INSERT INTO produtos (nomeProduto, unidade, quantidade, custoUnitario, localizacao) VALUES
-- Matérias-primas
('Cimento CP II', 'Saco', 100, 28.50, 'GALPÃO MATÉRIAS-PRIMAS'),
('Areia Lavada', 'm³', 20, 85.00, 'GALPÃO MATÉRIAS-PRIMAS'),
('Brita 0', 'm³', 15, 120.00, 'GALPÃO MATÉRIAS-PRIMAS'),
('Brita 1', 'm³', 12, 115.00, 'GALPÃO MATÉRIAS-PRIMAS'),
('Pigmento Vermelho', 'Kg', 50, 12.80, 'ALMOXARIFADO'),
('Pigmento Cinza', 'Kg', 45, 11.50, 'ALMOXARIFADO'),
('Pigmento Preto', 'Kg', 30, 14.20, 'ALMOXARIFADO'),
('Aditivo Plastificante', 'Litro', 25, 8.90, 'ALMOXARIFADO'),
('Fibra de Polipropileno', 'Kg', 18, 22.50, 'ALMOXARIFADO'),
('Cal Hidratada', 'Saco', 30, 15.80, 'GALPÃO MATÉRIAS-PRIMAS'),

-- Blocos Estruturais
('Bloco Estrutural 14x19x39cm', 'Milheiro', 120, 1900.00, 'PÁTIO ESTRUTURAIS'),
('Bloco Estrutural 9x19x39cm', 'Milheiro', 90, 1600.00, 'PÁTIO ESTRUTURAIS'),
('Bloco Canaleta 14x19x39cm', 'Milheiro', 25, 2800.00, 'PÁTIO ESTRUTURAIS'),
('Bloco de Vedação 9x19x39cm', 'Milheiro', 80, 1450.00, 'PÁTIO ESTRUTURAIS'),
('Meio Bloco 9x19x19cm', 'Milheiro', 40, 950.00, 'PÁTIO ESTRUTURAIS'),

-- Blocos de Vedação
('Bloco Cerâmico 9x19x19cm', 'Milheiro', 70, 1250.00, 'PÁTIO VEDAÇÃO'),
('Bloco Cerâmico 14x19x19cm', 'Milheiro', 60, 1350.00, 'PÁTIO VEDAÇÃO'),
('Bloco de Concreto Celular', 'Milheiro', 35, 4200.00, 'PÁTIO VEDAÇÃO'),

-- Blocos de Pavimentação
('Bloco de Pavimentação 10x20x6cm', 'm²', 500, 120.00, 'PÁTIO PAVIMENTAÇÃO'),
('Bloco Intertravado 6cm', 'm²', 450, 150.00, 'PÁTIO PAVIMENTAÇÃO'),
('Bloco Intertravado 8cm', 'm²', 380, 180.00, 'PÁTIO PAVIMENTAÇÃO'),
('Bloco dreno 20x20x7cm', 'm²', 320, 135.00, 'PÁTIO PAVIMENTAÇÃO'),
('Bloco Grama 30x30x8cm', 'm²', 280, 220.00, 'PÁTIO PAVIMENTAÇÃO'),

-- Blocos Decorativos
('Bloco de Fachada Texturizado', 'Milheiro', 25, 3800.00, 'GALPÃO DECORATIVOS'),
('Bloco de Vidro 19x19x19cm', 'Caixa', 40, 420.00, 'GALPÃO DECORATIVOS'),
('Bloco Cobogó', 'Unidade', 800, 12.50, 'GALPÃO DECORATIVOS'),

-- Equipamentos e Ferramentas
('Forma para Bloco 14x19x39cm', 'Unidade', 80, 120.00, 'OFICINA'),
('Forma para Bloco 9x19x39cm', 'Unidade', 70, 110.00, 'OFICINA'),
('Vibrador de Mesa', 'Unidade', 3, 2800.00, 'OFICINA'),
('Betoneira 400L', 'Unidade', 2, 4200.00, 'OFICINA'),
('Cortadora de Blocos', 'Unidade', 4, 1850.00, 'OFICINA'),
('Compactador Manual', 'Unidade', 5, 890.00, 'OFICINA'),

-- Embalagens
('Pallet Plástico', 'Unidade', 50, 85.00, 'GALPÃO EMBALAGENS'),
('Filme Stretch', 'Rolo', 30, 28.50, 'GALPÃO EMBALAGENS'),
('Cinta de Nylon', 'Pacote', 15, 12.80, 'GALPÃO EMBALAGENS'),

-- Produtos Químicos
('Desmoldante', 'Litro', 40, 18.90, 'ALMOXARIFADO QUÍMICOS'),
('Impermeabilizante', 'Litro', 25, 32.50, 'ALMOXARIFADO QUÍMICOS'),
('Selador para Blocos', 'Litro', 35, 45.80, 'ALMOXARIFADO QUÍMICOS');

-- Consulta para visualizar todos os produtos com sua localização
SELECT idProduto, nomeProduto, unidade, quantidade, custoUnitario, localizacao 
FROM produtos 
ORDER BY localizacao, nomeProduto;

-- Consulta para buscar produtos por localização específica
SELECT idProduto, nomeProduto, unidade, quantidade, custoUnitario 
FROM produtos 
WHERE localizacao = 'PÁTIO ESTRUTURAIS';

-- Consulta para buscar produtos por nome e mostrar localização
SELECT nomeProduto, unidade, quantidade, localizacao 
FROM produtos 
WHERE nomeProduto LIKE '%Bloco%' 
ORDER BY localizacao, nomeProduto;

-- Relatório de produtos por localização
SELECT 
    localizacao,
    COUNT(*) AS Total_Produtos,
    SUM(quantidade) AS Total_Itens,
    ROUND(SUM(quantidade * custoUnitario), 2) AS Valor_Total
FROM produtos 
GROUP BY localizacao
ORDER BY localizacao;

-- Consulta para verificar estoque baixo (menos de 20 unidades)
SELECT nomeProduto, unidade, quantidade, localizacao
FROM produtos
WHERE quantidade < 20
ORDER BY localizacao, nomeProduto;