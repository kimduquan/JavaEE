export PGPASSWORD=090323508
psql -h localhost -d epf -U postgres -p 5432 -a -w -f "./EPF-schema/src/main/resources/META-INF/persistence/data.sql"
CREATE USER openpg WITH CREATEDB CREATEROLE PASSWORD 'openpgpwd';
CREATE DATABASE odoo WITH OWNER = openpg;