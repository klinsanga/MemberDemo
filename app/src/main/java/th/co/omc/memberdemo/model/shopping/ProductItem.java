package th.co.omc.memberdemo.model.shopping;

import com.android.tonyvu.sc.model.Saleable;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Created by teera-s on 10/18/2016 AD.
 */

public class ProductItem implements Saleable, Serializable {

    private String productThumbs;
    private String productName;
    private String productCode;
    private String productPV;
    private String productDesc;
    private String productPrice;
    public boolean selected;

    public ProductItem(String productThumbs, String productName, String productCode, String productPV, String productDesc, String productPrice) {
        this.productThumbs = productThumbs;

        this.productName = productName;
        this.productCode = productCode;
        this.productPV = productPV;
        this.productDesc = productDesc;
        this.productPrice = productPrice;
    }


    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public String getProductPV() {
        return productPV;
    }

    public void setProductPV(String productPV) {
        this.productPV = productPV;
    }

    public String getProductDesc() {
        return productDesc;
    }

    public void setProductDesc(String productDesc) {
        this.productDesc = productDesc;
    }

    public String getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(String productPrice) {
        this.productPrice = productPrice;
    }

    public String getProductThumbs() {
        return productThumbs;
    }

    public void setProductThumbs(String productThumbs) {
        this.productThumbs = productThumbs;
    }

    @Override
    public BigDecimal getPrice() {
        return null;
    }

    @Override
    public String getName() {
        return null;
    }
}
