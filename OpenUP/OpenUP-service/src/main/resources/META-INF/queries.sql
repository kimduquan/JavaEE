SELECT GRANTEDROLE 
FROM INFORMATION_SCHEMA.RIGHTS 
WHERE 
    GRANTEETYPE = 'ROLE' 
    AND (
        GRANTEE 
            IN (
                SELECT GRANTEDROLE 
                FROM INFORMATION_SCHEMA.RIGHTS 
                WHERE GRANTEETYPE = 'USER' 
                    AND GRANTEE = ?
                )
        OR
        GRANTEDROLE 
            IN (
                SELECT GRANTEDROLE 
                FROM INFORMATION_SCHEMA.RIGHTS 
                WHERE GRANTEETYPE = 'USER' 
                    AND GRANTEE = ?
                )
        )
UNION
SELECT GRANTEE 
FROM INFORMATION_SCHEMA.RIGHTS 
WHERE 
    GRANTEETYPE = 'ROLE' 
    AND (
        GRANTEE 
            IN (
                SELECT GRANTEDROLE 
                FROM INFORMATION_SCHEMA.RIGHTS 
                WHERE GRANTEETYPE = 'USER' 
                    AND GRANTEE = ?
                )
        OR
        GRANTEDROLE 
            IN (
                SELECT GRANTEDROLE 
                FROM INFORMATION_SCHEMA.RIGHTS 
                WHERE GRANTEETYPE = 'USER' 
                    AND GRANTEE = ?
                )
        );