-- Estados de solicitacao
INSERT INTO estado_solicitacao (descricao)
SELECT 'ABERTA'
    WHERE NOT EXISTS (SELECT 1 FROM estado_solicitacao WHERE descricao = 'ABERTA');

INSERT INTO estado_solicitacao (descricao)
SELECT 'ORÇADA'
    WHERE NOT EXISTS (SELECT 1 FROM estado_solicitacao WHERE descricao = 'ORÇADA');

INSERT INTO estado_solicitacao (descricao)
SELECT 'REJEITADA'
    WHERE NOT EXISTS (SELECT 1 FROM estado_solicitacao WHERE descricao = 'REJEITADA');

INSERT INTO estado_solicitacao (descricao)
SELECT 'APROVADA'
    WHERE NOT EXISTS (SELECT 1 FROM estado_solicitacao WHERE descricao = 'APROVADA');

INSERT INTO estado_solicitacao (descricao)
SELECT 'REDIRECIONADA'
    WHERE NOT EXISTS (SELECT 1 FROM estado_solicitacao WHERE descricao = 'REDIRECIONADA');

INSERT INTO estado_solicitacao (descricao)
SELECT 'ARRUMADA'
    WHERE NOT EXISTS (SELECT 1 FROM estado_solicitacao WHERE descricao = 'ARRUMADA');

INSERT INTO estado_solicitacao (descricao)
SELECT 'PAGA'
    WHERE NOT EXISTS (SELECT 1 FROM estado_solicitacao WHERE descricao = 'PAGA');

INSERT INTO estado_solicitacao (descricao)
SELECT 'FINALIZADA'
    WHERE NOT EXISTS (SELECT 1 FROM estado_solicitacao WHERE descricao = 'FINALIZADA');



-- // Categorias de Equipamento
INSERT INTO categoria_equipamento (descricao)
SELECT 'Notebook'
    WHERE NOT EXISTS (SELECT 1 FROM categoria_equipamento WHERE descricao = 'Notebook');

INSERT INTO categoria_equipamento (descricao)
SELECT 'Desktop'
    WHERE NOT EXISTS (SELECT 1 FROM categoria_equipamento WHERE descricao = 'Desktop');

INSERT INTO categoria_equipamento (descricao)
SELECT 'Impressora'
    WHERE NOT EXISTS (SELECT 1 FROM categoria_equipamento WHERE descricao = 'Impressora');

INSERT INTO categoria_equipamento (descricao)
SELECT 'Mouse'
    WHERE NOT EXISTS (SELECT 1 FROM categoria_equipamento WHERE descricao = 'Mouse');

INSERT INTO categoria_equipamento (descricao)
SELECT 'Teclado'
    WHERE NOT EXISTS (SELECT 1 FROM categoria_equipamento WHERE descricao = 'Teclado');