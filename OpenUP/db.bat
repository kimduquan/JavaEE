start kubectl port-forward svc/postgresql 5432:5432
set PGPASSWORD=090323508
"C:\Program Files\PostgreSQL\14\bin\psql.exe" -h localhost -d epf -U postgres -p 5432 -a -w -f "./EPF-schema/src/main/resources/META-INF/persistence/data.sql"