package epf.persistence.util;

public interface SQLStateClasses {

	String successful_completion = "00000";
	String warning = "01000";
	String no_data = "02000";
	String sql_statement_not_yet_complete = "03000";
	String connection_exception = "08000";
	String triggered_action_exception = "09000";
	String feature_not_supported = "0A000";
	String invalid_transaction_initiation = "0B000";
	String locator_exception = "0F000";
	String invalid_grantor = "0L000";
	String invalid_role_specification = "0P000";
	String diagnostics_exception = "0Z000";
	String invalid_argument_for_xquery = "10608";
	String case_not_found = "20000";
	String cardinality_violation = "21000";
	String data_exception = "22000";
	String integrity_constraint_violation = "23000";
	String invalid_cursor_state = "24000";
	String invalid_transaction_state = "25000";
	String invalid_sql_statement_name = "26000";
	String triggered_data_change_violation = "27000";
	String invalid_authorization_specification = "28000";
	String dependent_privilege_descriptors_still_exist = "2B000";
	String invalid_transaction_termination = "2D000";
	String sql_routine_exception = "2F000";
	String invalid_cursor_name = "34000";
	String external_routine_exception = "38000";
	String external_routine_invocation_exception = "39000";
	String savepoint_exception = "3B000";
	String invalid_catalog_name = "3D000";
	String invalid_schema_name = "3F000";
	String transaction_rollback = "40000";
	String syntax_error_or_access_rule_violation = "42000";
	String with_check_option_violation = "44000";
	String insufficient_resources = "53000";
	String program_limit_exceeded = "54000";
	String object_not_in_prerequisite_state = "55000";
	String operator_intervention = "57000";
	String system_error = "58000";
	String config_file_error = "F0000";
	String fdw_error = "HV000";
	String plpgsql_error = "P0000";
	String internal_error = "XX000";
}
