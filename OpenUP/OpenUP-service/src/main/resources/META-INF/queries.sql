SELECT GRANTEDROLE
FROM INFORMATION_SCHEMA.RIGHTS
WHERE GRANTEETYPE = 'ROLE'
    AND GRANTEDROLE <> ''
    AND GRANTEE
        IN  (
            SELECT GRANTEDROLE 
            FROM INFORMATION_SCHEMA.RIGHTS 
            WHERE GRANTEETYPE = 'USER'
                AND GRANTEE = ?
                AND GRANTEDROLE <> ''
                )
UNION
    (
    SELECT GRANTEDROLE 
    FROM INFORMATION_SCHEMA.RIGHTS 
    WHERE GRANTEETYPE = 'USER'
        AND GRANTEE = ?
        AND GRANTEDROLE <> ''
);