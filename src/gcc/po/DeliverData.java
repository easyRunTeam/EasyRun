package gcc.po;

import java.io.Serializable;

/**
 * �ջ���ַ����
 * Created by gecongcong on 2016/6/25.
 */
public class DeliverData implements Serializable{
    private String deliverAddress = "";//�ջ��˵�ַ
    private String deliverName = "";//�ջ�������
    private String deliverCode = "";//�ʱ�
    private String deliverPhone = "";//�ջ�����ϵ��ʽ

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
