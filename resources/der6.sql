-- PostgreSQL

create table sessao(
	sessao_id serial,
	usuario varchar(128),
	entrada timestamp,
	primary key(sessao_id)
);

create table sensor(
	sensor_id serial,
	nome varchar(128),
	descricao varchar(256),
	primary key(sensor_id)
);

create table regiao(
	regiao_id serial,
	caracteristica varchar(128),
	descricao varchar(128),
	primary key(regiao_id)
);

create table imagem_info(
	imagem_info_id serial,
	envio timestamp,
	regiao_id integer,
	foreign key(regiao_id) references regiao(regiao_id) on delete cascade on update cascade,
	sensor_id integer,
	foreign key(sensor_id) references sensor(sensor_id) on delete cascade on update cascade,
	primary key(imagem_info_id)
);

create table imagem(
	imagem_id serial,
	extencao varchar(260),
	arquivo bytea,
	imagem_info_id integer,
	foreign key(imagem_info_id) references imagem_info(imagem_info_id) on delete cascade on update cascade,
	primary key(imagem_id)
);

create table campo(
	campo_id serial,
	nome varchar(128),
	primary key(campo_id)
);

create table propriedade(
	propriedade_id serial,
	valora real,
	valorb real,
	imagem_info_id integer,
	campo_id integer,
	foreign key(campo_id) references campo(campo_id) on delete cascade on update cascade,
	foreign key(imagem_info_id) references imagem_info(imagem_info_id) on delete cascade on update cascade,
	primary key(propriedade_id)
);

create table bovino(
	bovino_id serial,
	sexo boolean,
	raca varchar(128),
	nascimento date,
	primary key(bovino_id)
);

create table bovino_imagem_info(
	bovino_imagem_info_id serial,
	bovino_id integer,
	imagem_info_id integer,
	foreign key(bovino_id) references bovino(bovino_id) on delete cascade on update cascade,
	foreign key(imagem_info_id) references imagem_info(imagem_info_id) on delete cascade on update cascade,
	primary key(bovino_imagem_info_id)
);