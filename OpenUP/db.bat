start kubectl port-forward svc/postgresql 5432:5432
set PGPASSWORD=090323508
call "C:\Program Files\PostgreSQL\14\bin\psql.exe" -h localhost -d postgres -U postgres -p 5432 -a -w -f "./EPF-schema/src/main/resources/META-INF/persistence/data.sql"