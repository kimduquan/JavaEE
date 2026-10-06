Set-NetIPInterface `
    -InterfaceAlias "vEthernet (WSL)" `
    -AddressFamily IPv4 `
    -Forwarding Enabled

Set-NetIPInterface `
    -InterfaceAlias "vEthernet (Default Switch)" `
    -AddressFamily IPv4 `
    -Forwarding Enabled

Set-VMProcessor -VMName "epf-node" -ExposeVirtualizationExtensions $true
New-NetFirewallRule `
    -DisplayName "Allow TCP 25000" `
    -Direction Inbound `
    -Protocol TCP `
    -LocalPort 25000 `
    -Action Allow
New-NetFirewallRule `
    -DisplayName "Allow TCP 16443" `
    -Direction Inbound `
    -Protocol TCP `
    -LocalPort 16443 `
    -Action Allow