package com.quanpv.model;

import com.quanpv.utils.Slug;

import javax.persistence.*;
import java.util.Date;


@Entity
@Table(name = "PRODUCT")
public class Product {

    @Id
    @Column(name = "PRODUCT_ID")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private String isdn;
    private String name;
    private String slug;
    private String description;
    private String image;
    private String imageList;
    private String color;
    private String size;
    private float price;
    private float salePrice;
    private int quantity ;
    private Date createdDate;
    private Date updatedDate;
    private boolean isPopular;
    private boolean isHighlighted;
    private boolean isDraft;

    @ManyToOne
    @JoinColumn(name = "CATEGORY_ID")
    private Category category;

    public Product() {

    }

    public Product(String isdn, String name, String description, String image, String imageList, float price,
                   int quantity) {
        this.isdn = isdn;
        this.name = name;
        this.description = description;
        this.image = image;
        this.imageList = imageList;
        this.price = price;
        this.quantity = quantity;
        this.createdDate = new Date();
        this.updatedDate = this.createdDate;
        this.slug = getDefaultSlug();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getIsdn() {
        return isdn;
    }

    public void setIsdn(String isdn) {
        this.isdn = isdn;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getImageList() {
        return imageList;
    }

    public void setImageList(String imageList) {
        this.imageList = imageList;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public float getSalePrice() {
        return salePrice;
    }

    public void setSalePrice(float salePrice) {
        this.salePrice = salePrice;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Date getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(Date updatedDate) {
        this.updatedDate = updatedDate;
    }

    public boolean isPopular() {
        return isPopular;
    }

    public void setPopular(boolean popular) {
        isPopular = popular;
    }

    public boolean isHighlighted() {
        return isHighlighted;
    }

    public void setHighlighted(boolean highlighted) {
        isHighlighted = highlighted;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    private String getDefaultSlug() {
        return Slug.makeSlug(name);
    }

    public boolean isDraft() {
        return isDraft;
    }

    public void setDraft(boolean draft) {
        isDraft = draft;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", isdn='" + isdn + '\'' +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", image='" + image + '\'' +
                ", imageList='" + imageList + '\'' +
                ", color='" + color + '\'' +
                ", size='" + size + '\'' +
                ", price=" + price +
                ", salePrice=" + salePrice +
                ", quantity=" + quantity +
                ", createdDate=" + createdDate +
                ", updatedDate=" + updatedDate +
                ", isPopular=" + isPopular +
                ", isHighlighted=" + isHighlighted +
                ", category=" + category +
                '}';
    }
}
