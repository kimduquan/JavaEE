psql -U postgres < template.sql
psql -U postgres -d epf_template < template_data.sql
psql -U postgres -d epf_template < template_grant.sql