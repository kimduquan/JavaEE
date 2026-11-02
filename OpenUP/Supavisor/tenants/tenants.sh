. ../../env.sh ../../
../../secret.sh postgres.json
../../secret.sh epf.json
../../secret.sh keycloak.json
../../secret.sh odoo.json
../../secret.sh gitea.json
./import.sh postgres.json postgres
./import.sh epf.json epf
./import.sh keycloak.json keycloak
./import.sh odoo.json odoo
./import.sh gitea.json gitea