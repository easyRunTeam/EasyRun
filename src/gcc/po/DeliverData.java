package gcc.po;

import java.io.Serializable;

/**
 * 收货地址数据
 * Created by gecongcong on 2016/6/25.
 */
public class DeliverData implements Serializable{
    private String deliverAddress = "";//收货人地址
    private String deliverName = "";//收货人姓名
    private String deliverCode = "";//邮编
    private String deliverPhone = "";//收货人联系方式

    public DeliverData(){}

    public String getDeliverAddress() {
        return deliverAddress;
    }

    public void setDeliverAddress(String deliverAddress) {
        this.deliverAddress = deliverAddress;
    }

    public String getDeliverName() {
        return deliverName;
    }

    public void setDeliverName(String deliverName) {
        this.deliverName = deliverName;
    }

    public String getDeliverCode() {
        return deliverCode;
    }

    public void setDeliverCode(String deliverCode) {
        this.deliverCode = deliverCode;
    }

    public String getDeliverPhone() {
        return deliverPhone;
    }

    public void setDeliverPhone(String deliverPhone) {
        this.deliverPhone = deliverPhone;
    }
}
