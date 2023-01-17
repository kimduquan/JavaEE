kubectl port-forward svc/postgresql 5432:5432 &
export PGPASSWORD=090323508
psql -h localhost -d epf -U postgres -p 5432 -a -w -f "./EPF-schema/src/main/resources/META-INF/persistence/data.sql"