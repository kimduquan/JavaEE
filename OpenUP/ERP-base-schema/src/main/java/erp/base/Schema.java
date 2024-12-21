package erp.base;

import java.util.Date;

public class Schema {
// Decimal Precision
class DecimalPrecision {
	String name; // Usage
	int digits = 2; // Digits
}
class Model {
}
class Data {
}
enum BindingType {
	action, // Action
	report, // Report
}
enum ViewType {
	list, // List
	form, // Form
	graph, // Graph
	pivot, // Pivot
	calendar, // Calendar
	kanban, // Kanban
	search, // Search
	qweb, // QWeb
}
class ModelAccess {
}
class Rule {
}
class Menu {
}
class ModuleCategory {
}
// Access Groups
class Group {
	String name;
	User[] users;
	ModelAccess[] model_access; // Access Controls
	Rule[] rule_groups;
	Menu[] menu_access; // Access Menu
	View[] view_access; // Views
	String comment;
	ModuleCategory category; // Application
	int color; // Color Index
	String full_name; // Group Name
	boolean share; // Share Group
	float api_key_duration; // API Keys maximum duration days
}
// Users Log
class UserLog {
}
enum Mode {
	primary, // Base view
	extension // Extension View
}
// View
class View {
	String name; // View Name
	String model;
	String key;
	int priority = 16; // Sequence
	ViewType type; // View Type
	String arch; // View Architecture
	String arch_base; // Base View Architecture
	String arch_db; // Arch Blob
	String arch_fs; // Arch Filename
	boolean arch_updated; // Modified Architecture
	String arch_prev; // Previous View Architecture
	View inherit;// Inherited View
	View[] inherit_childrens; // Views which inherit from this one
	Data model_data; // Model Data
	String xml_id; // External ID
	Group[] groups; // Groups
	Mode mode = Mode.primary; // View inheritance mode
	String warning_info; // Warning information
	boolean active = true;
	Model model_; // Model of the view
}
// Actions
class Action {
	String name; // Action Name
	String type; // Action Type
	String xml_id; // External ID
	String path; // Path to show in the URL
	String help; // Action Description
	Model binding_model;
	BindingType binding_type = BindingType.action;
	String binding_view_types = "list,form";
}
enum TargetWindow {
	current, // Current Window
	new_, // New Window
	fullscreen, // Full Screen
	main, // Main action of Current Window
}
// Action Window View
class WindowView {
	int sequence;
	View view; // View
	ViewType view_mode; // View Type
	Act_Window act_window; // Action
	boolean multi; // On Multiple Doc.
}
class Filter {
	
}
// Embedded Actions
class EmbeddedAction {
	String name;
	int sequence;
	Act_Window parent_action; // Parent Action
	int parent_res_id; // Active Parent Id
	String parent_res_model; // Active Parent Model
	Action action; // Action
	String python_method;
	User user; // User
	boolean is_deletable;
	String default_view_mode; // Default View
	Filter[] filters;
	boolean is_visible; // Embedded visibility
	String domain = "[]";
	String context = "{}";
	Group[] groups;
}
// Exports Line
class Line {
	String name; // Field Name
	Export export; // Export
}
// Exports
class Export {
	String name; // Export Name
	String resource;
	Line[] export_fields; // Export ID
}
// Action Window
class Act_Window extends Action {
	String type = "ir.actions.act_window";
	View view; // View Ref.
	String domain; // Domain Value
	String context = ""; // Context Value
	int res_id; // Record ID
	String res_model; // Destination Model
	TargetWindow target = TargetWindow.current; // Target Window
	String view_mode = "list,form";
	String mobile_view_mode = "kanban";
	String usage; // Action Usage
	WindowView[] views_; // No of Views
	byte[] views;
	int limit = 80;
	Group[] groups; // Groups
	View search_view; // Search View Ref.
	EmbeddedAction[] embedded_actions;
	boolean filter;
}
// Action Window Close
class Act_Window_Close extends Action {
	String type = "ir.actions.act_window_close";
}
enum ActionTarget {
	new_, // New Window
	self, // This Window
	download, // Download
}
// Action URL
class Act_Url extends Action {
	String type = "ir.actions.act_url";
	String url; // Action URL
	ActionTarget target = ActionTarget.new_; // Action Target
}
enum Usage {
	ir_actions_server, // Server Action
    ir_cron, // Scheduled Action
}
enum Type {
	object_write, // Update Record
    object_create, // Create Record
    code, // Execute Code
    webhook, // Send Webhook Notification
    multi, // Execute Existing Actions
}
class Field {
	
}
enum Operation {
	add, // Adding
    remove, // Removing
    set, // Setting it to
    clear, // Clearing it
}
enum ValueType {
	value, // Update
    equation, // Compute
}
class Selection {
	
}
enum ValueFieldToShow {
	value, // value
    resource_ref, // reference
    update_boolean_value, // update_boolean_value
    selection_value, // selection_value
}
// Server Actions
class Server extends Action {
	String name;
	String type = "ir.actions.server";
	Usage usage = Usage.ir_actions_server; // Usage
	Type state = Type.object_write; // Type
	int sequence = 5;
	Model model; // Model
	Model[] available_models; // Available Models
	String model_name;
	String code; // Python Code
	Server[] childs; // Child Actions
	Model crud_model; // Record to Create
	String crud_model_name; // Target Model Name
	Field link_field; // Link Field
	Group[] groups; // Allowed Groups
	Field update_field; // Field to Update
	String update_path; // Field to Update Path
	Model update_related_model;
	String update_field_type;
	Operation update_m2m_operation = Operation.add; // Many2many Operations
	boolean update_boolean_value = true; // Boolean Value
	String value;
	ValueType evaluation_type = ValueType.value; // Value Type
	Object resource_ref; // Record
	Selection selection_value; // Custom Value
	ValueFieldToShow value_field_to_show;
	String webhook_url; // Webhook URL
	Field[] webhook_fields; // Webhook Fields
	String webhook_sample_payload; // Sample Payload
}
enum Status {
	open, // To Do
	done, // Done
}
// Configuration Wizards
class Todo {
	Action action; // Action
	int sequence = 10;
	Status state = Status.open;
	String name;
}
// Client Action
class Client extends Action {
	String type = "ir.actions.client";
	String tag; // Client action tag
	TargetWindow target;
	String res_model; // Destination Model
	String context = ""; // Context Value
	byte[] params; // Supplementary arguments
	byte[] params_store; // Params storage
}
// System Parameter
class Config_Parameter {
	String key;
	String value;
}
enum IntervalUnit {
	minutes, // Minutes
    hours, // Hours
    days, // Days
    weeks, // Weeks'),
    months, // Months
}
// Scheduled Actions
class Cron {
	Server ir_actions_server; // Server action
	String cron_name; // Name
	User user; // Scheduler User
	boolean active = true;
	int interval_number = 1;
	IntervalUnit interval_type = IntervalUnit.months; // Interval Unit
	Date nextcall; // Next Execution Date
	Date lastcall; // Last Execution Date
	int priority = 5;
	int failure_count = 0;
	Date first_failure_date; // First Failure Date
}
// Triggered actions
class CronTrigger {
	Cron cron;
	Date call_at;
}
// Progress of Scheduled Actions
class CronProgress {
	Cron cron;
	int remaining = 0;
	int done = 0;
	boolean deactivate;
	int timed_out_counter = 0;
}
// Default Values
class Default {
	Field field; // Field
	User user; // User
	Company company; // Company
	String condition; // Condition
	String json_value; // Default Value (JSON format)
}
enum ReportType {
	qweb_html, // HTML
	qweb_pdf, // PDF
	qweb_text, // Text
}
class Paperformat {
}
// Report Action
class Report extends Action {
	String type = "ir.actions.report";
	BindingType binding_type = BindingType.report;
	String model; // Model Name
	Model model_; // Model
	ReportType report_type = ReportType.qweb_pdf;
	String report_name; // Template Name
	String report_file; // Report File
	Group[] groups; // Groups
	boolean multi; // On Multiple Doc.
	Paperformat paperformat; // Paper Format
	String print_report_name; // Printed Report Name
	boolean attachment_use; // Reload from Attachment
	String attachment; // Save as Attachment Prefix
	String domain; // Filter domain
}
enum Directive {
	append, // Append
	prepend, // Prepend
	after, // After
	before, // Before
	remove, // Remove
	replace, // Replace
	include // Include
}
// Asset
class Asset {
	String name; // Name
	String bundle; // Bundle name
	Directive directive = Directive.append; // Directive
	String path; // Path (or glob pattern)
	String target; // Target
	boolean active = true; // active
	int sequence = 16; // Sequence
}
// Partner Title
class PartnerTitle {
	String name; // Title
	String shortcut; // Abbreviation
}
// Users API Keys
class APIKey {
	String name; // Description
	User user;
	String scope;
	Date create_date; // Creation Date
	Date expiration_date; // Expiration Date
}
enum DeviceType {
	computer, // Computer
	mobile, // Mobile
}
// Device Log
class DeviceLog {
	String session_identifier; // Session Identifier
	String platform; // Platform
	String browser; // Browser
	String ip_address; // IP Address
	String country; // Country
	String city; // City
	DeviceType device_type; // Device Type
	User user;
	Date first_activity; // First Activity
	Date last_activity; // Last Activity
	boolean revoked; // Revoked
	boolean is_current; // Current Device
	String linked_ip_addresses; // Linked IP address
}
// Devices
class Device extends DeviceLog {
}
// User Settings
class UserSetting {
	User user; // User
}
// User
class User extends Partner {
	Partner partner; // Related Partner
	String login;
	String password;
	String new_password; // Set Password
	APIKey[] api_keys; // API Keys
	String signature; // Email Signature
	boolean active = true;
	boolean active_partner; // Partner is Active
	Action action; // Home Action
	Group[] groups; // Groups
	UserLog[] logs; // User log entries
	Device[] devices; // User devices
	Date login_date; // Latest authentication
	boolean share; // Share User
	int companies_count; // Number of Companies
	String tz_offset; // Timezone offset
	UserSetting[] res_users_settings;
	UserSetting res_users_setting; // Settings
	Company company; // Company
	Company[] companies; // Companies
	String name;
	String email;
	int accesses_count; // Access Rights
	int rules_count; // Record Rules
	int groups_count; // Groups
}
// Partner Tags
class PartnerCategory {
	String name; // Name
	int color; // Color
	PartnerCategory parent; // Category
	PartnerCategory[] childs; // Child Tags
	boolean active = true;
	String parent_path;
	Partner[] partners; // Partners
}
enum AddressType {
	contact, // Contact
	invoice, // Invoice Address
	delivery, // Delivery Address
	other, // Other Address
}
// Industry
class PartnerIndustry {
	String name; // Name
	String full_name; // Full Name
	boolean active = true;
}
enum CompanyType {
	person, // Individual
	company, // Company
}
// Contact
class Partner {
	String name;
	String complete_name;
	PartnerTitle title;
	Partner parent; // Related Company
	String parent_name; // Parent name
	Partner[] childs; // Contact
	String ref; // Reference
	String lang; // Language
	int active_lang_count;
	String tz; // Timezone
	String tz_offset; // Timezone offset
	User user; // Salesperson
	String vat; // Tax ID
	Partner same_vat_partner; // Partner with same Tax ID
	Partner same_company_registry_partner; // Partner with same Company Registry
	String company_registry; // Company ID
	PartnerBank[] banks; // Banks
	String website; // Website Link
	String comment; // Notes
	PartnerCategory category; // Tags
	boolean active = true;
	boolean employee;
	String function; // Job Position
	AddressType type = AddressType.contact; // Address Type
	String street;
	String street2;
	String zip;
	String city;
	CountryState state; // State
	Country country; // Country
	String country_code; // Country Code
	float partner_latitude; // Geo Latitude
	float partner_longitude; // Geo Longitude
	String email;
	String email_formatted; // Formatted Email
	String phone;
	String mobile;
	boolean is_company; // Is a Company
	boolean is_public;
	PartnerIndustry industry; // Industry
	CompanyType company_type; // Company Type
	Company company; // Company
	int color; // Color Index
	User[] users; // Users
	boolean partner_share; // Share Partner
	String contact_address; // Complete Address
	Partner commercial_partner; // Commercial Entity
	String commercial_company_name; // Company Name Entity
	String company_name; // Company Name
	String barcode;
}
// Currency Rate
class CurrencyRate {
	Date name; // Date
	float rate; // Technical Rate
	float company_rate;
	float inverse_company_rate;
	Currency currency; // Currency
	Company company; // Company
}
enum SymbolPosition {
	after, // After Amount
	before, // Before Amount
}
// Currency
class Currency {
	String name; // Currency
	int iso_numeric; // Currency numeric code.
	String full_name; // Name
	String symbol;
	float rate; // Current Rate
	float inverse_rate;
	String rate_string;
	CurrencyRate[] rates; // Rates
	float rounding = 0.01f; // Rounding Factor
	int decimal_places;
	boolean active = true;
	SymbolPosition position = SymbolPosition.after; // Symbol Position
	Date date;
	String currency_unit_label; // Currency Unit
	String currency_subunit_label; // Currency Subunit
	boolean is_current_company_currency;
}
// Country Group
class CountryGroup {
	String name;
	Country[] countries; // Countries
}
// Country state
class CountryState {
	Country country; // Country
	String name; // State Name
	String code; // State Code
}
enum NamePosition {
	before, // Before Address
	after // After Address
}
// Country
class Country {
	String name; // Country Name
	String code; // Country Code
	String address_format; // Layout in Reports
	View address_view; // Input View
	Currency currency; // Currency
	String image_url;
	int phone_code; // Country Calling Code
	CountryGroup[] country_groups; // Country Groups
	CountryState[] countryStates; // States
	NamePosition name_position = NamePosition.before; // Customer Name Position
	String vat_label; // Vat Label
	boolean state_required = false;
	boolean zip_required = true;
}
// Bank
class Bank {
	String name;
	String street;
	String street2;
	String zip;
	String city;
	CountryState countryState; // Fed. State
	Country country;
	String country_code; // Country Code
	String email;
	String phone;
	boolean active = true;
	String bic; // Bank Identifier Code
}
enum Font {
	Lato, // Lato
	Roboto, // Roboto
	Open_Sans, // Open Sans
	Montserrat, // Montserrat
	Oswald, // Oswald
	Raleway, // Raleway
	Tajawal, // Tajawal
	Fira_Mono, // Fira Mono
}
enum LayoutBackground {
	Blank, // Blank
	Demo_logo, // Demo logo
	Custom, // Custom'
}
class Module {
}
// Companies
class Company {
	String name; // Company Name
	boolean active = true;
	int sequence = 10;
	Company parent; // Parent Company
	Company[] childs; // Branches
	Company[] all_childs;
	String parent_path;
	Company[] parents;
	Company root;
	Partner partner; // Partner
	String report_header; // Company Tagline
	String report_footer; // Report Footer
	String company_details; // Company Details
	boolean is_company_details_empty;
	byte[] logo; // Company Logo
	byte[] logo_web;
	boolean uses_default_logo;
	Currency currency; // Currency
	User[] users; // Accepted Users
	String street;
	String street2;
	String zip;
	String city;
	CountryState countryState; // Fed. State
	Bank[] banks;
	Country country; // Country
	String country_code;
	String email;
	String phone;
	String mobile;
	String website;
	String vat; // Tax ID
	String company_registry; // Company ID
	Paperformat paperformat; // Paper format
	View external_report_layout; // Document Template
	Font font = Font.Lato;
	String primary_color;
	String secondary_color;
	int color;
	LayoutBackground layout_background = LayoutBackground.Blank;
	byte[] layout_background_image; // Background Image
	Module[] uninstalled_l10n_modules;
}
enum AttachmentType {
	url, // URL
	binary // File
}
// Attachment
class Attachment {
	String name; // Name
	String description; // Description
	String res_name; // Resource Name
	String res_model; // Resource Model
	String res_field; // Resource Field
	String res_id; // Resource ID
	Company company; // Company
	AttachmentType type = AttachmentType.binary; // Type
	String url; // URL
	boolean public_; // Is public document
	String access_token; // Access Token
	byte[] raw; // File Content (raw)
	byte[] datas; // File Content (base64)
	byte[] db_datas; // Database Data
	String store_fname; // Stored Filename
	int file_size; // File Size
	String checksum; // Checksum/SHA1
	String mimetype; // Mime Type
	String index_content; // Indexed Content
}
enum BankAccountType {
	Normal, IBAN
}
// Bank Accounts
class PartnerBank {
	boolean active = true;
	BankAccountType acc_type; // Type. Bank account type: Normal or IBAN. Inferred from the bank account
							// number.
	String acc_number; // Account Number
	String sanitized_acc_number; // Sanitized Account Number
	String acc_holder_name; // Account Holder Name
	Partner partner; // Account Holder
	boolean allow_out_payment; // Send Money
	Bank bank; // Bank
	String bank_name;
	String bank_bic;
	int sequence = 10;
	Currency currency; // Currency
	Company company; // Company
	String country_code; // Country Code
}
enum Applicability {
	accounts, // Accounts
	taxes, // Taxes
	products, // Products
}
// Account Tag
class AccountTag {
	String name; // Tag Name
	Applicability applicability = Applicability.accounts;
	int color; // Color Index
	boolean active = true;
	boolean tax_negate; // Negate Tax Balance
	Country country; // Country
}
enum AccountType {
	asset_receivable, // Receivable
    asset_cash, // Bank and Cash
    asset_current, // Current Assets
    asset_non_current, // Non-current Assets
    asset_prepayments, // Prepayments
    asset_fixed, // Fixed Assets
    liability_payable, // Payable
    liability_credit_card, // Credit Card
    liability_current, // Current Liabilities
    liability_non_current, // Non-current Liabilities
    equity, // Equity
    equity_unaffected, // Current Year Earnings
    income, // Income
    income_other, // Other Income
    expense, // Expenses
    expense_depreciation, // Depreciation
    expense_direct_cost, // Cost of Revenue
    off_balance, // Off-Balance Sheet
}
enum InternalGroup {
	equity, //Equity
    asset, // Asset
    liability, // Liability
    income, // Income
    expense, // Expense
    off, // Off Balance
}
class Tax {
	
}
class CodeMapping {
	
}
// Account Group
class AccountGroup {
	AccountGroup parent;
	String name;
	String code_prefix_start;
	String code_prefix_end;
	Company company;
}
class Root {
	
}
class Journal {
	
}
// Account
class Account {
	String name; // Account Name
	Currency currency; // Account Currency
	Currency company_currency;
	String company_fiscal_country_code;
	String code;
	String code_store;
	String placeholder_code; // Display code
	boolean deprecated = false;
	boolean used;
	AccountType account_type; // Type
	boolean include_initial_balance; // Bring Accounts Balance Forward
	InternalGroup internal_group; // Internal Group
	boolean reconcile; // Allow Reconciliation
	Tax[] taxes; // Default Taxes
	String note; // Internal Notes
	Company[] companies; // Companies
	CodeMapping[] code_mappings;
	AccountTag tags; // Tags
	AccountGroup group;
	Root root;
	Journal[] allowed_journals; // Allowed Journals
	String opening_debit; // Opening Debit
	String opening_credit; // Opening Credit
	String opening_balance; // Opening Balance
	float current_balance;
	int related_taxes_amount;
	boolean non_trade = false;
	boolean display_mapping_tab;
}
class AnalyticAccount extends Account {
	int invoice_count; // Invoice Count
	int vendor_bill_count; // Vendor Bill Count
	String debit;
	String credit;
}
class DistributionModel {
	
}
class Product {
	
}
class ProductCategory {
	
}
class AnalyticDistributionModel extends DistributionModel{
	String account_prefix; // Accounts Prefix
	Product product; // Product
	ProductCategory product_categ; // Product Category
	String prefix_placeholder;
}
class MoveLine {
	
}
enum LineCategory {
	invoice, // Customer Invoice
	vendor_bill, // Vendor Bill
}
// Analytic Line
class AnalyticLine {
	Product product; // Product
	Account general_account; // Financial Account
	Journal journal; // Financial Journal
	Partner partner;
	MoveLine move_line; // Journal Item
	String code;
	String ref; // Ref.
	LineCategory category;
}
// Analytic Plan's Applicabilities
class AnalyticApplicability {
	String business_domain;
	String account_prefix; // Financial Accounts Prefix
	ProductCategory product_categ; // Product Category
	boolean display_account_prefix;
}
class Move {
	
}
class Statement {
	
}
class Payment {
	
}
// Bank Statement Line
class BankStatementLine extends Move {
	Move move; // Journal Entry
	Journal journal;
	Company company;
	Statement statement; // Statement
	Payment[] payments; // Auto-generated Payments
	int sequence = 1;
	Partner partner; // Partner
	String account_number; // Bank Account Number
	String partner_name;
	String transaction_type;
	String payment_ref; // Label
	Currency currency; // Journal Currency
	String amount;
	String running_balance;
	Currency foreign_currency; // Foreign Currency
	String amount_currency; // Amount in Currency
	float amount_residual; // Residual Amount
	String country_code;
	String internal_index; // Internal Reference
	boolean is_reconciled; // Is Reconciled
	boolean statement_complete;
	boolean statement_valid;
	String statement_balance_end_real;
	String statement_name; // Statement Name
	Object transaction_details;
}
class AccountMove extends Move {
	BankStatementLine[] statement_lines; // Statements
}
}
