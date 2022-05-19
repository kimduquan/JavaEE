CALL FTL_DROP_INDEX('EPF_DELIVERY_PROCESSES', 'DELIVERY_PROCESS');
CALL FTL_DROP_INDEX('EPF_DELIVERY_PROCESSES', 'PHASE');
CALL FTL_DROP_INDEX('EPF_DELIVERY_PROCESSES', 'CAPABILITY_PATTERN');
CALL FTL_DROP_INDEX('EPF_DELIVERY_PROCESSES', 'ACTIVITY');
CALL FTL_DROP_INDEX('EPF_DELIVERY_PROCESSES', 'ITERATION');
CALL FTL_DROP_INDEX('EPF_DELIVERY_PROCESSES', 'MILESTONE');

CALL FTL_DROP_INDEX('EPF_ROLES', 'ROLE_SET');
CALL FTL_DROP_INDEX('EPF_ROLES', 'EPF_ROLE');
CALL FTL_DROP_INDEX('EPF_ROLES', 'ROLES');

CALL FTL_DROP_INDEX('EPF_WORK_PRODUCTS', 'EPF_DOMAIN');
CALL FTL_DROP_INDEX('EPF_WORK_PRODUCTS', 'ARTIFACT');
CALL FTL_DROP_INDEX('EPF_WORK_PRODUCTS', 'DELIVERABLE');
CALL FTL_DROP_INDEX('EPF_WORK_PRODUCTS', 'WORK_PRODUCTS');
CALL FTL_DROP_INDEX('EPF_WORK_PRODUCTS', 'WORK_PRODUCT_SLOT');
CALL FTL_DROP_INDEX('EPF_WORK_PRODUCTS', 'ROLE_MODIFIES');
CALL FTL_DROP_INDEX('EPF_WORK_PRODUCTS', 'ARTIFACT_FULFILLED_SLOTS');

CALL FTL_DROP_INDEX('EPF_TASKS', 'DISCIPLINE');
CALL FTL_DROP_INDEX('EPF_TASKS', 'TASK');
CALL FTL_DROP_INDEX('EPF_TASKS', 'TASKS');
CALL FTL_DROP_INDEX('EPF_TASKS', 'ROLE_ADDITIONALLY_PERFORMS');
CALL FTL_DROP_INDEX('EPF_TASKS', 'TASK_INPUTS_MANDATORY');
CALL FTL_DROP_INDEX('EPF_TASKS', 'TASK_INPUTS_OPTIONAL');
CALL FTL_DROP_INDEX('EPF_TASKS', 'TASK_OUTPUTS');

DROP TABLE FTL.INDEXES;
DROP SCHEMA FTL;