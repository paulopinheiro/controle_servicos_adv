--
-- PostgreSQL database dump
--

SET statement_timeout = 0;
SET lock_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SET check_function_bodies = false;
SET client_min_messages = warning;

--
-- Name: plpgsql; Type: EXTENSION; Schema: -; Owner: 
--

CREATE EXTENSION IF NOT EXISTS plpgsql WITH SCHEMA pg_catalog;


--
-- Name: EXTENSION plpgsql; Type: COMMENT; Schema: -; Owner: 
--

COMMENT ON EXTENSION plpgsql IS 'PL/pgSQL procedural language';


SET search_path = public, pg_catalog;

SET default_tablespace = '';

SET default_with_oids = false;

--
-- Name: advogado; Type: TABLE; Schema: public; Owner: controladv; Tablespace: 
--

CREATE TABLE advogado (
    id integer NOT NULL,
    oab character(10) NOT NULL,
    digest_password character varying(255),
    nome character varying(255) NOT NULL,
    administrador boolean DEFAULT false NOT NULL,
    ativo boolean DEFAULT true NOT NULL
);


ALTER TABLE advogado OWNER TO controladv;

--
-- Name: advogado_id_seq; Type: SEQUENCE; Schema: public; Owner: controladv
--

CREATE SEQUENCE advogado_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE advogado_id_seq OWNER TO controladv;

--
-- Name: advogado_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: controladv
--

ALTER SEQUENCE advogado_id_seq OWNED BY advogado.id;


--
-- Name: cliente; Type: TABLE; Schema: public; Owner: controladv; Tablespace: 
--

CREATE TABLE cliente (
    id bigint NOT NULL,
    cpf_cnpj character varying(20),
    nome character varying(255) NOT NULL,
    qualificacao character varying(800),
    telefone character varying(40),
    email character varying(60),
    rede_social character varying(255),
    ativo boolean DEFAULT true NOT NULL,
    advogado_id integer NOT NULL,
    endereco character varying(800)
);


ALTER TABLE cliente OWNER TO controladv;

--
-- Name: cliente_id_seq; Type: SEQUENCE; Schema: public; Owner: controladv
--

CREATE SEQUENCE cliente_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE cliente_id_seq OWNER TO controladv;

--
-- Name: cliente_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: controladv
--

ALTER SEQUENCE cliente_id_seq OWNED BY cliente.id;


--
-- Name: conta_servico; Type: TABLE; Schema: public; Owner: controladv; Tablespace: 
--

CREATE TABLE conta_servico (
    id bigint NOT NULL,
    valor numeric(8,2) DEFAULT 0 NOT NULL,
    servico_prestado_id bigint NOT NULL
);


ALTER TABLE conta_servico OWNER TO controladv;

--
-- Name: conta_servico_id_seq; Type: SEQUENCE; Schema: public; Owner: controladv
--

CREATE SEQUENCE conta_servico_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE conta_servico_id_seq OWNER TO controladv;

--
-- Name: conta_servico_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: controladv
--

ALTER SEQUENCE conta_servico_id_seq OWNED BY conta_servico.id;


--
-- Name: documento; Type: TABLE; Schema: public; Owner: controladv; Tablespace: 
--

CREATE TABLE documento (
    id bigint NOT NULL,
    tipo_documento_id integer NOT NULL,
    cliente_id bigint NOT NULL,
    numero character varying(60) NOT NULL,
    orgao_emissor character varying(255)
);


ALTER TABLE documento OWNER TO controladv;

--
-- Name: documento_id_seq; Type: SEQUENCE; Schema: public; Owner: controladv
--

CREATE SEQUENCE documento_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE documento_id_seq OWNER TO controladv;

--
-- Name: documento_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: controladv
--

ALTER SEQUENCE documento_id_seq OWNED BY documento.id;


--
-- Name: historico; Type: TABLE; Schema: public; Owner: controladv; Tablespace: 
--

CREATE TABLE historico (
    id bigint NOT NULL,
    data_historico date NOT NULL,
    descricao character varying(800) NOT NULL,
    cliente_id bigint NOT NULL
);


ALTER TABLE historico OWNER TO controladv;

--
-- Name: historico_id_seq; Type: SEQUENCE; Schema: public; Owner: controladv
--

CREATE SEQUENCE historico_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE historico_id_seq OWNER TO controladv;

--
-- Name: historico_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: controladv
--

ALTER SEQUENCE historico_id_seq OWNED BY historico.id;


--
-- Name: parcela; Type: TABLE; Schema: public; Owner: controladv; Tablespace: 
--

CREATE TABLE parcela (
    id bigint NOT NULL,
    conta_servico_id bigint NOT NULL,
    valor numeric(8,2) DEFAULT 0 NOT NULL,
    data_vencimento date NOT NULL,
    data_pagamento date
);


ALTER TABLE parcela OWNER TO controladv;

--
-- Name: parcela_id_seq; Type: SEQUENCE; Schema: public; Owner: controladv
--

CREATE SEQUENCE parcela_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE parcela_id_seq OWNER TO controladv;

--
-- Name: parcela_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: controladv
--

ALTER SEQUENCE parcela_id_seq OWNED BY parcela.id;


--
-- Name: parceria_servico; Type: TABLE; Schema: public; Owner: controladv; Tablespace: 
--

CREATE TABLE parceria_servico (
    id bigint NOT NULL,
    servico_prestado_id bigint NOT NULL,
    advogado_id integer NOT NULL,
    detalhes character varying(400)
);


ALTER TABLE parceria_servico OWNER TO controladv;

--
-- Name: parceria_servico_id_seq; Type: SEQUENCE; Schema: public; Owner: controladv
--

CREATE SEQUENCE parceria_servico_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE parceria_servico_id_seq OWNER TO controladv;

--
-- Name: parceria_servico_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: controladv
--

ALTER SEQUENCE parceria_servico_id_seq OWNED BY parceria_servico.id;


--
-- Name: repasse_escritorio; Type: TABLE; Schema: public; Owner: controladv; Tablespace: 
--

CREATE TABLE repasse_escritorio (
    id bigint NOT NULL,
    parcela_id bigint NOT NULL,
    valor numeric(8,2) DEFAULT 0 NOT NULL,
    data_repasse date,
    observacao character varying(400)
);


ALTER TABLE repasse_escritorio OWNER TO controladv;

--
-- Name: repasse_escritorio_id_seq; Type: SEQUENCE; Schema: public; Owner: controladv
--

CREATE SEQUENCE repasse_escritorio_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE repasse_escritorio_id_seq OWNER TO controladv;

--
-- Name: repasse_escritorio_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: controladv
--

ALTER SEQUENCE repasse_escritorio_id_seq OWNED BY repasse_escritorio.id;


--
-- Name: repasse_parceria; Type: TABLE; Schema: public; Owner: controladv; Tablespace: 
--

CREATE TABLE repasse_parceria (
    id bigint NOT NULL,
    parcela_id bigint NOT NULL,
    parceria_servico_id bigint NOT NULL,
    valor numeric(8,2) DEFAULT 0 NOT NULL,
    data_repasse date,
    observacao character varying(400)
);


ALTER TABLE repasse_parceria OWNER TO controladv;

--
-- Name: repasse_parceria_id_seq; Type: SEQUENCE; Schema: public; Owner: controladv
--

CREATE SEQUENCE repasse_parceria_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE repasse_parceria_id_seq OWNER TO controladv;

--
-- Name: repasse_parceria_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: controladv
--

ALTER SEQUENCE repasse_parceria_id_seq OWNED BY repasse_parceria.id;


--
-- Name: servico_prestado; Type: TABLE; Schema: public; Owner: controladv; Tablespace: 
--

CREATE TABLE servico_prestado (
    id bigint NOT NULL,
    cliente_id bigint NOT NULL,
    data_prestacao date NOT NULL,
    advogado_id integer NOT NULL,
    detalhes character varying(800),
    tipo_servico_id integer NOT NULL,
    observacao character varying(800)
);


ALTER TABLE servico_prestado OWNER TO controladv;

--
-- Name: servico_prestado_id_seq; Type: SEQUENCE; Schema: public; Owner: controladv
--

CREATE SEQUENCE servico_prestado_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE servico_prestado_id_seq OWNER TO controladv;

--
-- Name: servico_prestado_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: controladv
--

ALTER SEQUENCE servico_prestado_id_seq OWNED BY servico_prestado.id;


--
-- Name: tipo_documento; Type: TABLE; Schema: public; Owner: controladv; Tablespace: 
--

CREATE TABLE tipo_documento (
    id integer NOT NULL,
    nome character varying(40) NOT NULL
);


ALTER TABLE tipo_documento OWNER TO controladv;

--
-- Name: tipo_documento_id_seq; Type: SEQUENCE; Schema: public; Owner: controladv
--

CREATE SEQUENCE tipo_documento_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE tipo_documento_id_seq OWNER TO controladv;

--
-- Name: tipo_documento_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: controladv
--

ALTER SEQUENCE tipo_documento_id_seq OWNED BY tipo_documento.id;


--
-- Name: tipo_servico; Type: TABLE; Schema: public; Owner: controladv; Tablespace: 
--

CREATE TABLE tipo_servico (
    id integer NOT NULL,
    nome character varying(100) NOT NULL
);


ALTER TABLE tipo_servico OWNER TO controladv;

--
-- Name: tipo_servico_id_seq; Type: SEQUENCE; Schema: public; Owner: controladv
--

CREATE SEQUENCE tipo_servico_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE tipo_servico_id_seq OWNER TO controladv;

--
-- Name: tipo_servico_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: controladv
--

ALTER SEQUENCE tipo_servico_id_seq OWNED BY tipo_servico.id;


--
-- Name: id; Type: DEFAULT; Schema: public; Owner: controladv
--

ALTER TABLE ONLY advogado ALTER COLUMN id SET DEFAULT nextval('advogado_id_seq'::regclass);


--
-- Name: id; Type: DEFAULT; Schema: public; Owner: controladv
--

ALTER TABLE ONLY cliente ALTER COLUMN id SET DEFAULT nextval('cliente_id_seq'::regclass);


--
-- Name: id; Type: DEFAULT; Schema: public; Owner: controladv
--

ALTER TABLE ONLY conta_servico ALTER COLUMN id SET DEFAULT nextval('conta_servico_id_seq'::regclass);


--
-- Name: id; Type: DEFAULT; Schema: public; Owner: controladv
--

ALTER TABLE ONLY documento ALTER COLUMN id SET DEFAULT nextval('documento_id_seq'::regclass);


--
-- Name: id; Type: DEFAULT; Schema: public; Owner: controladv
--

ALTER TABLE ONLY historico ALTER COLUMN id SET DEFAULT nextval('historico_id_seq'::regclass);


--
-- Name: id; Type: DEFAULT; Schema: public; Owner: controladv
--

ALTER TABLE ONLY parcela ALTER COLUMN id SET DEFAULT nextval('parcela_id_seq'::regclass);


--
-- Name: id; Type: DEFAULT; Schema: public; Owner: controladv
--

ALTER TABLE ONLY parceria_servico ALTER COLUMN id SET DEFAULT nextval('parceria_servico_id_seq'::regclass);


--
-- Name: id; Type: DEFAULT; Schema: public; Owner: controladv
--

ALTER TABLE ONLY repasse_escritorio ALTER COLUMN id SET DEFAULT nextval('repasse_escritorio_id_seq'::regclass);


--
-- Name: id; Type: DEFAULT; Schema: public; Owner: controladv
--

ALTER TABLE ONLY repasse_parceria ALTER COLUMN id SET DEFAULT nextval('repasse_parceria_id_seq'::regclass);


--
-- Name: id; Type: DEFAULT; Schema: public; Owner: controladv
--

ALTER TABLE ONLY servico_prestado ALTER COLUMN id SET DEFAULT nextval('servico_prestado_id_seq'::regclass);


--
-- Name: id; Type: DEFAULT; Schema: public; Owner: controladv
--

ALTER TABLE ONLY tipo_documento ALTER COLUMN id SET DEFAULT nextval('tipo_documento_id_seq'::regclass);


--
-- Name: id; Type: DEFAULT; Schema: public; Owner: controladv
--

ALTER TABLE ONLY tipo_servico ALTER COLUMN id SET DEFAULT nextval('tipo_servico_id_seq'::regclass);


--
-- Data for Name: advogado; Type: TABLE DATA; Schema: public; Owner: controladv
--

COPY advogado (id, oab, digest_password, nome, administrador, ativo) FROM stdin;
2	43521/SC  	\N	ANDRÉA CRISTINA MARCELLINO	t	t
6	19243/SC  	\N	MÔNICA REGINA PEREIRA KIENAST	t	t
7	41232     	\N	THAYSA C. BARBOZA FERREIRA	t	t
\.


--
-- Name: advogado_id_seq; Type: SEQUENCE SET; Schema: public; Owner: controladv
--

SELECT pg_catalog.setval('advogado_id_seq', 7, true);


--
-- Data for Name: cliente; Type: TABLE DATA; Schema: public; Owner: controladv
--

COPY cliente (id, cpf_cnpj, nome, qualificacao, telefone, email, rede_social, ativo, advogado_id, endereco) FROM stdin;
1	82192863934	PAULO SÉRGIO PINHEIRO		47 3319 3961	pinus.sc@gmail.com		t	2	R. Francisco Romão, 30 - Bairro São Pedro - Navegantes - SC
\.


--
-- Name: cliente_id_seq; Type: SEQUENCE SET; Schema: public; Owner: controladv
--

SELECT pg_catalog.setval('cliente_id_seq', 6, true);


--
-- Data for Name: conta_servico; Type: TABLE DATA; Schema: public; Owner: controladv
--

COPY conta_servico (id, valor, servico_prestado_id) FROM stdin;
\.


--
-- Name: conta_servico_id_seq; Type: SEQUENCE SET; Schema: public; Owner: controladv
--

SELECT pg_catalog.setval('conta_servico_id_seq', 1, false);


--
-- Data for Name: documento; Type: TABLE DATA; Schema: public; Owner: controladv
--

COPY documento (id, tipo_documento_id, cliente_id, numero, orgao_emissor) FROM stdin;
19	1	1	4/R 2.689.202	SSP/SC
\.


--
-- Name: documento_id_seq; Type: SEQUENCE SET; Schema: public; Owner: controladv
--

SELECT pg_catalog.setval('documento_id_seq', 27, true);


--
-- Data for Name: historico; Type: TABLE DATA; Schema: public; Owner: controladv
--

COPY historico (id, data_historico, descricao, cliente_id) FROM stdin;
\.


--
-- Name: historico_id_seq; Type: SEQUENCE SET; Schema: public; Owner: controladv
--

SELECT pg_catalog.setval('historico_id_seq', 1, false);


--
-- Data for Name: parcela; Type: TABLE DATA; Schema: public; Owner: controladv
--

COPY parcela (id, conta_servico_id, valor, data_vencimento, data_pagamento) FROM stdin;
\.


--
-- Name: parcela_id_seq; Type: SEQUENCE SET; Schema: public; Owner: controladv
--

SELECT pg_catalog.setval('parcela_id_seq', 1, false);


--
-- Data for Name: parceria_servico; Type: TABLE DATA; Schema: public; Owner: controladv
--

COPY parceria_servico (id, servico_prestado_id, advogado_id, detalhes) FROM stdin;
\.


--
-- Name: parceria_servico_id_seq; Type: SEQUENCE SET; Schema: public; Owner: controladv
--

SELECT pg_catalog.setval('parceria_servico_id_seq', 1, false);


--
-- Data for Name: repasse_escritorio; Type: TABLE DATA; Schema: public; Owner: controladv
--

COPY repasse_escritorio (id, parcela_id, valor, data_repasse, observacao) FROM stdin;
\.


--
-- Name: repasse_escritorio_id_seq; Type: SEQUENCE SET; Schema: public; Owner: controladv
--

SELECT pg_catalog.setval('repasse_escritorio_id_seq', 1, false);


--
-- Data for Name: repasse_parceria; Type: TABLE DATA; Schema: public; Owner: controladv
--

COPY repasse_parceria (id, parcela_id, parceria_servico_id, valor, data_repasse, observacao) FROM stdin;
\.


--
-- Name: repasse_parceria_id_seq; Type: SEQUENCE SET; Schema: public; Owner: controladv
--

SELECT pg_catalog.setval('repasse_parceria_id_seq', 1, false);


--
-- Data for Name: servico_prestado; Type: TABLE DATA; Schema: public; Owner: controladv
--

COPY servico_prestado (id, cliente_id, data_prestacao, advogado_id, detalhes, tipo_servico_id, observacao) FROM stdin;
1	1	2016-05-26	2		2	
2	1	2016-05-10	2		1	
\.


--
-- Name: servico_prestado_id_seq; Type: SEQUENCE SET; Schema: public; Owner: controladv
--

SELECT pg_catalog.setval('servico_prestado_id_seq', 3, true);


--
-- Data for Name: tipo_documento; Type: TABLE DATA; Schema: public; Owner: controladv
--

COPY tipo_documento (id, nome) FROM stdin;
1	RG
2	CTPS
3	PIS
\.


--
-- Name: tipo_documento_id_seq; Type: SEQUENCE SET; Schema: public; Owner: controladv
--

SELECT pg_catalog.setval('tipo_documento_id_seq', 3, true);


--
-- Data for Name: tipo_servico; Type: TABLE DATA; Schema: public; Owner: controladv
--

COPY tipo_servico (id, nome) FROM stdin;
1	INICIAL
2	ACORDO EXTRAJUDICIAL
3	RECURSO
4	INVENTÁRIO
\.


--
-- Name: tipo_servico_id_seq; Type: SEQUENCE SET; Schema: public; Owner: controladv
--

SELECT pg_catalog.setval('tipo_servico_id_seq', 5, true);


--
-- Name: pk_advogado; Type: CONSTRAINT; Schema: public; Owner: controladv; Tablespace: 
--

ALTER TABLE ONLY advogado
    ADD CONSTRAINT pk_advogado PRIMARY KEY (id);


--
-- Name: pk_cliente; Type: CONSTRAINT; Schema: public; Owner: controladv; Tablespace: 
--

ALTER TABLE ONLY cliente
    ADD CONSTRAINT pk_cliente PRIMARY KEY (id);


--
-- Name: pk_conta_servico; Type: CONSTRAINT; Schema: public; Owner: controladv; Tablespace: 
--

ALTER TABLE ONLY conta_servico
    ADD CONSTRAINT pk_conta_servico PRIMARY KEY (id);


--
-- Name: pk_documento; Type: CONSTRAINT; Schema: public; Owner: controladv; Tablespace: 
--

ALTER TABLE ONLY documento
    ADD CONSTRAINT pk_documento PRIMARY KEY (id);


--
-- Name: pk_historico; Type: CONSTRAINT; Schema: public; Owner: controladv; Tablespace: 
--

ALTER TABLE ONLY historico
    ADD CONSTRAINT pk_historico PRIMARY KEY (id);


--
-- Name: pk_parcela; Type: CONSTRAINT; Schema: public; Owner: controladv; Tablespace: 
--

ALTER TABLE ONLY parcela
    ADD CONSTRAINT pk_parcela PRIMARY KEY (id);


--
-- Name: pk_pareceria_servico; Type: CONSTRAINT; Schema: public; Owner: controladv; Tablespace: 
--

ALTER TABLE ONLY parceria_servico
    ADD CONSTRAINT pk_pareceria_servico PRIMARY KEY (id);


--
-- Name: pk_repasse_escritorio; Type: CONSTRAINT; Schema: public; Owner: controladv; Tablespace: 
--

ALTER TABLE ONLY repasse_escritorio
    ADD CONSTRAINT pk_repasse_escritorio PRIMARY KEY (id);


--
-- Name: pk_repasse_parceria; Type: CONSTRAINT; Schema: public; Owner: controladv; Tablespace: 
--

ALTER TABLE ONLY repasse_parceria
    ADD CONSTRAINT pk_repasse_parceria PRIMARY KEY (id);


--
-- Name: pk_servico_prestado; Type: CONSTRAINT; Schema: public; Owner: controladv; Tablespace: 
--

ALTER TABLE ONLY servico_prestado
    ADD CONSTRAINT pk_servico_prestado PRIMARY KEY (id);


--
-- Name: pk_tipo_documento; Type: CONSTRAINT; Schema: public; Owner: controladv; Tablespace: 
--

ALTER TABLE ONLY tipo_documento
    ADD CONSTRAINT pk_tipo_documento PRIMARY KEY (id);


--
-- Name: pk_tipo_servico; Type: CONSTRAINT; Schema: public; Owner: controladv; Tablespace: 
--

ALTER TABLE ONLY tipo_servico
    ADD CONSTRAINT pk_tipo_servico PRIMARY KEY (id);


--
-- Name: unq_advogado_oab; Type: CONSTRAINT; Schema: public; Owner: controladv; Tablespace: 
--

ALTER TABLE ONLY advogado
    ADD CONSTRAINT unq_advogado_oab UNIQUE (oab);


--
-- Name: unq_conta_servico_servico_prestado; Type: CONSTRAINT; Schema: public; Owner: controladv; Tablespace: 
--

ALTER TABLE ONLY conta_servico
    ADD CONSTRAINT unq_conta_servico_servico_prestado UNIQUE (servico_prestado_id);


--
-- Name: unq_parcela_conta_servico_data_vencimento; Type: CONSTRAINT; Schema: public; Owner: controladv; Tablespace: 
--

ALTER TABLE ONLY parcela
    ADD CONSTRAINT unq_parcela_conta_servico_data_vencimento UNIQUE (conta_servico_id, data_vencimento);


--
-- Name: unq_parceria_servico_advogado_servico; Type: CONSTRAINT; Schema: public; Owner: controladv; Tablespace: 
--

ALTER TABLE ONLY parceria_servico
    ADD CONSTRAINT unq_parceria_servico_advogado_servico UNIQUE (servico_prestado_id, advogado_id);


--
-- Name: unq_repasse_escritorio_parcela_id; Type: CONSTRAINT; Schema: public; Owner: controladv; Tablespace: 
--

ALTER TABLE ONLY repasse_escritorio
    ADD CONSTRAINT unq_repasse_escritorio_parcela_id UNIQUE (parcela_id);


--
-- Name: unq_repasse_parceria_parcela_parceria_servico; Type: CONSTRAINT; Schema: public; Owner: controladv; Tablespace: 
--

ALTER TABLE ONLY repasse_parceria
    ADD CONSTRAINT unq_repasse_parceria_parcela_parceria_servico UNIQUE (parcela_id, parceria_servico_id);


--
-- Name: unq_tipo_documento_nome; Type: CONSTRAINT; Schema: public; Owner: controladv; Tablespace: 
--

ALTER TABLE ONLY tipo_documento
    ADD CONSTRAINT unq_tipo_documento_nome UNIQUE (nome);


--
-- Name: unq_tipo_servico_nome; Type: CONSTRAINT; Schema: public; Owner: controladv; Tablespace: 
--

ALTER TABLE ONLY tipo_servico
    ADD CONSTRAINT unq_tipo_servico_nome UNIQUE (nome);


--
-- Name: fk_cliente_advogado; Type: FK CONSTRAINT; Schema: public; Owner: controladv
--

ALTER TABLE ONLY cliente
    ADD CONSTRAINT fk_cliente_advogado FOREIGN KEY (advogado_id) REFERENCES advogado(id);


--
-- Name: fk_conta_servico_servico_prestado; Type: FK CONSTRAINT; Schema: public; Owner: controladv
--

ALTER TABLE ONLY conta_servico
    ADD CONSTRAINT fk_conta_servico_servico_prestado FOREIGN KEY (servico_prestado_id) REFERENCES servico_prestado(id) ON UPDATE CASCADE ON DELETE CASCADE;


--
-- Name: fk_documento_cliente; Type: FK CONSTRAINT; Schema: public; Owner: controladv
--

ALTER TABLE ONLY documento
    ADD CONSTRAINT fk_documento_cliente FOREIGN KEY (cliente_id) REFERENCES cliente(id) ON UPDATE CASCADE ON DELETE CASCADE;


--
-- Name: fk_documento_tipo_documento; Type: FK CONSTRAINT; Schema: public; Owner: controladv
--

ALTER TABLE ONLY documento
    ADD CONSTRAINT fk_documento_tipo_documento FOREIGN KEY (tipo_documento_id) REFERENCES tipo_documento(id);


--
-- Name: fk_historico_cliente; Type: FK CONSTRAINT; Schema: public; Owner: controladv
--

ALTER TABLE ONLY historico
    ADD CONSTRAINT fk_historico_cliente FOREIGN KEY (cliente_id) REFERENCES cliente(id) ON UPDATE CASCADE ON DELETE CASCADE;


--
-- Name: fk_parcela_conta_servico; Type: FK CONSTRAINT; Schema: public; Owner: controladv
--

ALTER TABLE ONLY parcela
    ADD CONSTRAINT fk_parcela_conta_servico FOREIGN KEY (conta_servico_id) REFERENCES conta_servico(id) ON UPDATE CASCADE ON DELETE CASCADE;


--
-- Name: fk_parceria_servico_servico_prestado; Type: FK CONSTRAINT; Schema: public; Owner: controladv
--

ALTER TABLE ONLY parceria_servico
    ADD CONSTRAINT fk_parceria_servico_servico_prestado FOREIGN KEY (servico_prestado_id) REFERENCES servico_prestado(id) ON UPDATE CASCADE ON DELETE CASCADE;


--
-- Name: fk_pareceria_servico_advogado; Type: FK CONSTRAINT; Schema: public; Owner: controladv
--

ALTER TABLE ONLY parceria_servico
    ADD CONSTRAINT fk_pareceria_servico_advogado FOREIGN KEY (advogado_id) REFERENCES advogado(id);


--
-- Name: fk_repasse_escritorio_parcela; Type: FK CONSTRAINT; Schema: public; Owner: controladv
--

ALTER TABLE ONLY repasse_escritorio
    ADD CONSTRAINT fk_repasse_escritorio_parcela FOREIGN KEY (parcela_id) REFERENCES parcela(id) ON UPDATE CASCADE ON DELETE CASCADE;


--
-- Name: fk_repasse_parceria_parcela; Type: FK CONSTRAINT; Schema: public; Owner: controladv
--

ALTER TABLE ONLY repasse_parceria
    ADD CONSTRAINT fk_repasse_parceria_parcela FOREIGN KEY (parcela_id) REFERENCES parcela(id) ON UPDATE CASCADE ON DELETE CASCADE;


--
-- Name: fk_repasse_parceria_parceria_servico; Type: FK CONSTRAINT; Schema: public; Owner: controladv
--

ALTER TABLE ONLY repasse_parceria
    ADD CONSTRAINT fk_repasse_parceria_parceria_servico FOREIGN KEY (parceria_servico_id) REFERENCES parceria_servico(id) ON UPDATE CASCADE ON DELETE CASCADE;


--
-- Name: fk_servico_prestado_advogado; Type: FK CONSTRAINT; Schema: public; Owner: controladv
--

ALTER TABLE ONLY servico_prestado
    ADD CONSTRAINT fk_servico_prestado_advogado FOREIGN KEY (advogado_id) REFERENCES advogado(id);


--
-- Name: fk_servico_prestado_cliente; Type: FK CONSTRAINT; Schema: public; Owner: controladv
--

ALTER TABLE ONLY servico_prestado
    ADD CONSTRAINT fk_servico_prestado_cliente FOREIGN KEY (cliente_id) REFERENCES cliente(id);


--
-- Name: fk_servico_prestado_tipo_servico; Type: FK CONSTRAINT; Schema: public; Owner: controladv
--

ALTER TABLE ONLY servico_prestado
    ADD CONSTRAINT fk_servico_prestado_tipo_servico FOREIGN KEY (tipo_servico_id) REFERENCES tipo_servico(id);


--
-- Name: public; Type: ACL; Schema: -; Owner: postgres
--

REVOKE ALL ON SCHEMA public FROM PUBLIC;
REVOKE ALL ON SCHEMA public FROM postgres;
GRANT ALL ON SCHEMA public TO postgres;
GRANT ALL ON SCHEMA public TO PUBLIC;


--
-- Name: advogado; Type: ACL; Schema: public; Owner: controladv
--

REVOKE ALL ON TABLE advogado FROM PUBLIC;
REVOKE ALL ON TABLE advogado FROM controladv;
GRANT ALL ON TABLE advogado TO controladv;
GRANT ALL ON TABLE advogado TO PUBLIC;


--
-- Name: cliente; Type: ACL; Schema: public; Owner: controladv
--

REVOKE ALL ON TABLE cliente FROM PUBLIC;
REVOKE ALL ON TABLE cliente FROM controladv;
GRANT ALL ON TABLE cliente TO controladv;
GRANT ALL ON TABLE cliente TO PUBLIC;


--
-- Name: conta_servico; Type: ACL; Schema: public; Owner: controladv
--

REVOKE ALL ON TABLE conta_servico FROM PUBLIC;
REVOKE ALL ON TABLE conta_servico FROM controladv;
GRANT ALL ON TABLE conta_servico TO controladv;
GRANT ALL ON TABLE conta_servico TO PUBLIC;


--
-- Name: documento; Type: ACL; Schema: public; Owner: controladv
--

REVOKE ALL ON TABLE documento FROM PUBLIC;
REVOKE ALL ON TABLE documento FROM controladv;
GRANT ALL ON TABLE documento TO controladv;
GRANT ALL ON TABLE documento TO PUBLIC;


--
-- Name: historico; Type: ACL; Schema: public; Owner: controladv
--

REVOKE ALL ON TABLE historico FROM PUBLIC;
REVOKE ALL ON TABLE historico FROM controladv;
GRANT ALL ON TABLE historico TO controladv;
GRANT ALL ON TABLE historico TO PUBLIC;


--
-- Name: parcela; Type: ACL; Schema: public; Owner: controladv
--

REVOKE ALL ON TABLE parcela FROM PUBLIC;
REVOKE ALL ON TABLE parcela FROM controladv;
GRANT ALL ON TABLE parcela TO controladv;
GRANT ALL ON TABLE parcela TO PUBLIC;


--
-- Name: parceria_servico; Type: ACL; Schema: public; Owner: controladv
--

REVOKE ALL ON TABLE parceria_servico FROM PUBLIC;
REVOKE ALL ON TABLE parceria_servico FROM controladv;
GRANT ALL ON TABLE parceria_servico TO controladv;
GRANT ALL ON TABLE parceria_servico TO PUBLIC;


--
-- Name: repasse_escritorio; Type: ACL; Schema: public; Owner: controladv
--

REVOKE ALL ON TABLE repasse_escritorio FROM PUBLIC;
REVOKE ALL ON TABLE repasse_escritorio FROM controladv;
GRANT ALL ON TABLE repasse_escritorio TO controladv;
GRANT ALL ON TABLE repasse_escritorio TO PUBLIC;


--
-- Name: repasse_parceria; Type: ACL; Schema: public; Owner: controladv
--

REVOKE ALL ON TABLE repasse_parceria FROM PUBLIC;
REVOKE ALL ON TABLE repasse_parceria FROM controladv;
GRANT ALL ON TABLE repasse_parceria TO controladv;
GRANT ALL ON TABLE repasse_parceria TO PUBLIC;


--
-- Name: servico_prestado; Type: ACL; Schema: public; Owner: controladv
--

REVOKE ALL ON TABLE servico_prestado FROM PUBLIC;
REVOKE ALL ON TABLE servico_prestado FROM controladv;
GRANT ALL ON TABLE servico_prestado TO controladv;
GRANT ALL ON TABLE servico_prestado TO PUBLIC;


--
-- Name: tipo_documento; Type: ACL; Schema: public; Owner: controladv
--

REVOKE ALL ON TABLE tipo_documento FROM PUBLIC;
REVOKE ALL ON TABLE tipo_documento FROM controladv;
GRANT ALL ON TABLE tipo_documento TO controladv;
GRANT ALL ON TABLE tipo_documento TO PUBLIC;


--
-- Name: tipo_servico; Type: ACL; Schema: public; Owner: controladv
--

REVOKE ALL ON TABLE tipo_servico FROM PUBLIC;
REVOKE ALL ON TABLE tipo_servico FROM controladv;
GRANT ALL ON TABLE tipo_servico TO controladv;
GRANT ALL ON TABLE tipo_servico TO PUBLIC;


--
-- PostgreSQL database dump complete
--

