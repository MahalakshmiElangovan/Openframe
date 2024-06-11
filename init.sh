#!/bin/bash
set -e

# Wait for PostgreSQL to start
until pg_isready -h localhost -p 5432; do
  echo "Waiting for PostgreSQL to start..."
  sleep 2
done

# Run the SQL script if the database is not already initialized
if [ -z "$(psql -U "$POSTGRES_USER" -d "$POSTGRES_DB" -c '\dt')" ]; then
  psql -U "$POSTGRES_USER" -d "$POSTGRES_DB" -f /docker-entrypoint-initdb.d/data.sql
fi


java -jar your-application.jar

exec "$@"
