package erp.base.schema.ir.module.module;

import org.eclipse.microprofile.graphql.Description;

public enum Dep_State {
	@Description("Uninstallable")
	uninstallable,
	@Description("Not Installed")
	uninstalled,
	@Description("Installed")
	installed,
	@Description("To be upgraded")
	to_upgrade,
	@Description("To be removed")
	to_remove,
	@Description("To be installed")
	to_install,
	@Description("Unknown")
	unknown
}
