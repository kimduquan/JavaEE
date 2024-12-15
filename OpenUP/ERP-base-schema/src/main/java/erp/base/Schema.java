package erp.base;

import java.util.Date;

public class Schema {
// Decimal Precision
class DecimalPrecision {
    String name; // Usage
    int digits = 2; // Digits
};
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
    String help; // Action Description. Optional help text for the users with a description of the target view, such as its usage and purpose.
    Model binding_model;
    BindingType binding_type = BindingType.action;
    String binding_view_types = "list,form";
}
// Action Window
class Act_Window extends Action {
    String type = "ir.actions.act_window";
}
// Report Action
class Report extends Action {
    String type = "ir.actions.report"; // 
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
class Paperformat {   
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
enum AccountType {
    Normal,
    IBAN
}
// Bank Accounts
class PartnerBank {
    boolean active = true;
    AccountType acc_type; // Type. Bank account type: Normal or IBAN. Inferred from the bank account number.
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
}
