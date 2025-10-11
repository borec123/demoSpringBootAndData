--alter table person alter column id identity

/*
create table person (
id IDENTITY NOT NULL primary key,
first_name varchar(255),
last_name varchar(255),
avatar varchar(255),
score REAL
);

 create table action(
id IDENTITY not null primary key,
type varchar(50),
time bigint,
date1 date
);

 create table zprava(
id IDENTITY not null primary key,
cas_od TIMESTAMP ,
cas_do TIMESTAMP ,
zapnuto boolean,
titulek varchar(255),
zprava varchar(2048)
);

 create table zprava_archive (
id IDENTITY not null primary key,
cas_od TIMESTAMP ,
cas_do TIMESTAMP ,
zapnuto boolean,
titulek varchar(255),
zprava varchar(2048),
smazano char(1)
);




update ZPRAVA set ZPRAVA = 'Ruské vojenské letouny v Estonsku nebyly podle generálního tajemníka NATO Marka Rutteho minulý pátek bezprostřední hrozbou, proto je alianční letouny jen doprovodily ven z estonského vzdušného prostoru. Zasahovala tři letadla ze Švédska, Finska a Itálie. Aliance bude na podobné incidenty vždy reagovat s klidným odhodláním řekl Rutte s tím, že pokud to bude nutné, je NATO připraveno letadla vyhodnocená jako nebezpečí i sestřelit.
    „Náš vzkaz Rusům je jasný, budeme bránit každý centimetr našeho území,“ dodal Rutte.

    Rusko nese za nedávná narušení evropského vzdušného prostoru plnou zodpovědnost, uvedla předtím Severoatlantická aliance. Podle NATO takové chování ohrožuje životy, vyostřuje situaci a proto musí přestat. Reakce Aliance na „bezohledné činy Ruska“ bude i nadále razantní, stojí v jejím prohlášení. Chce se bránit všemi vojenskými i nevojenskými prostředky.' where id = 3
    
    
    
*/

--insert into ZPRAVA values (1, '2025-09-23 10:00:00', '2025-09-30 10:00:00', 'true', 'aaa', 'abcde ...')

--insert into person (first_name, last_name, avatar, score)  values ('Leo', 'Gudas', 'https://gravatar.com/avatar/99df1a5b2917db695be7ad69e46d9164?s=400&d=robohash&r=x', 100.0);
--insert into person (first_name, last_name, avatar, score) values ('Roman', 'Sikora', 'https://gravatar.com/avatar/99df1a5b2917db695be7ad69e46d9164?s=400&d=robohash&r=x', 100.0);

--create table aaa (a varchar(255))