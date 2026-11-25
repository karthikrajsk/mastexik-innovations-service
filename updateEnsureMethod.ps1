$path = 'd:\React Projects\Git Projects\Migration jvt-composite\Sizing module\report_module\composite_erp_server\erp-server\src\main\java\com\tech3\erp\service\PurchaseOrderService.java'
$content = Get-Content -LiteralPath $path -Raw
$old = @'
        String displayIdentifier = displayValue;
