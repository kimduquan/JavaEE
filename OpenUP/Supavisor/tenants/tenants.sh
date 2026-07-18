curl -X PUT http://localhost:4000/api/tenants/postgres -H "Content-Type: application/json" --data @postgres.json
curl -X PUT http://localhost:4000/api/tenants/epf -H "Content-Type: application/json" --data @epf.json
curl -X PUT http://localhost:4000/api/tenants/keycloak -H "Content-Type: application/json" --data @keycloak.json
curl -X PUT http://localhost:4000/api/tenants/odoo -H "Content-Type: application/json" --data @odoo.json
curl -X PUT http://localhost:4000/api/tenants/gitea -H "Content-Type: application/json" --data @gitea.json