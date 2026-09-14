Set-NetIPInterface `
    -InterfaceAlias "vEthernet (WSL)" `
    -AddressFamily IPv4 `
    -Forwarding Enabled

Set-NetIPInterface `
    -InterfaceAlias "vEthernet (Default Switch)" `
    -AddressFamily IPv4 `
    -Forwarding Enabled

Set-VMProcessor -VMName "epf-node" -ExposeVirtualizationExtensions $true