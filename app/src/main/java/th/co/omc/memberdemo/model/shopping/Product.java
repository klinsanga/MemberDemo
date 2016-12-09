package th.co.omc.memberdemo.model.shopping;

import com.android.tonyvu.sc.model.Saleable;

import java.io.Serializable;
import java.math.BigDecimal;

public class Product implements Saleable, Serializable {
    private static final long serialVersionUID = -4073256626483275668L;


    //private String productName;

    private String productCode;
    private String productDesc;
    private BigDecimal productPrice;
    private String productPV;
    private String productQty;
    private String productThumbs;
    private String productWeight;

    public Product() {
        super();
    }

    public Product(String productCode, String productDesc, BigDecimal productPrice, String productPV, String productQty, String productWeight, String productThumbs) {

        setProductCode(productCode);
        setProductDesc(productDesc);
        setProductPrice(productPrice);
        setProductPV(productPV);
        setProductQty(productQty);
        setProductWeight(productWeight);
        setProductThumbs(productThumbs);
    }

    @Override
    public boolean equals(Object o) {
        if (o == null) return false;
        if (!(o instanceof Product)) return false;

        return (this.productCode == ((Product) o).getProductCode());
    }

    public int hashCode() {
        final int prime = 31;
        int hash = 1;
        hash = hash * prime +  (productCode == null ? 0 : productCode.hashCode());;
        hash = hash * prime + (productDesc == null ? 0 : productDesc.hashCode());
        hash = hash * prime + (productPrice == null ? 0 : productPrice.hashCode());
        hash = hash * prime + (productPV == null ? 0 : productPV.hashCode());
        hash = hash * prime + (productQty == null ? 0 : productQty.hashCode());
        hash = hash * prime + (productThumbs == null ? 0 : productThumbs.hashCode());
        hash = hash * prime + (productWeight == null ? 0 : productWeight.hashCode());

        return hash;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    @Override
    public BigDecimal getPrice() {
        return productPrice;
    }

    @Override
    public String getName() {
        return productDesc;
    }

    public void setProductDesc(String productDesc) {
        this.productDesc = productDesc;
    }

    public void setProductPrice(BigDecimal productPrice) {
        this.productPrice = productPrice;
    }

    public String getProductPV() {
        return productPV;
    }

    public void setProductPV(String productPV) {
        this.productPV = productPV;
    }

    public String getProductQty() {
        return productQty;
    }

    public void setProductQty(String productQty) {
        this.productQty = productQty;
    }

    public String getProductThumbs() {
        return productThumbs;
    }

    public void setProductThumbs(String productThumbs) {
        this.productThumbs = productThumbs;
    }

    public String getProductWeight() {
        return productWeight;
    }

    public void setProductWeight(String productWeight) {
        this.productWeight = productWeight;
    }
}
