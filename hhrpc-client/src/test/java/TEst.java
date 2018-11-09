/**
 * @author liujiabei
 * @since 2018/11/8
 */
public class TEst {

    public static void main(String[] args) {
//        String itemUrl = "https://mapi.alipay.com/gateway.do?notify_url=http://test-proxy.pay.xunlei.com:14444/k8s_callback/v1/pay/unionpay/callback&sign_type=MD5&_input_charset=UTF-8&paymethod=bankPay&extend_param=security_wbInfo%5E%7B%22regDate%22%3A%22%22%2C%22regName%22%3A%221231231%22%2C%22depAccount%22%3A%221231231%22%7D&anti_phishing_key=KPwnEQEva8Wi3PSg5Q%3D%3D&defaultbank=ICBCB2C&service=create_direct_pay_by_user&total_fee=0.01&return_url=https%3A%2F%2Fbaidu.com&show_url=pay.xunlei.com&sign=b67cd2f1608cab45dd005b260383ad06&body=test11&seller_email=pay%40xunlei.com&partner=2088001226772997&subject=test&out_trade_no=20180830140511&payment_type=1&exter_invoke_ip=192.168.25.220";
//        String[] split = itemUrl.substring(itemUrl.indexOf("exter_invoke_ip")).split("&");
//        split = split[0].split("=");
//        Map<String, String> map = new HashMap<>();
//        map.put("exter_invoke_ip", split[1]);
//        map.put("ItemUrl", itemUrl);
//
//        System.out.println(map);
        String partner = getPartner2();
        System.out.println(partner);
    }

    public static String getPartner() {
        String v = "partner=11111";
        if (v.contains("partner")) {
            String[] arr = v.split("\\|");
            for (String str : arr) {
                String[] pair = str.split("=");
                if (pair.length == 2 && "partner".equals(pair[0])) {
                    return pair[1];
                }
            }
            return null;
        } else {
            return null;
        }
    }

    public static String getPartner2() {
        String v = "other2=0|partner=11111|aaa";
//        String v = "partner";
        if (v.contains("partner")) {
            String[] split = v.substring(v.indexOf("partner")).split("\\|")[0].split("=");
            return split.length>1 ? split[1] : null;
        }else{
            return null;
        }
    }



}
