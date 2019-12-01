<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" 
                xmlns:t="http://thanhnd.xml/pasgo.vn/"
                xmlns="http://thanhnd.xml/pasgo.vn/"
                version="1.0"
>
    <xsl:output method="xml" omit-xml-declaration="yes" indent="yes"/>
    <xsl:template match="t:places">
        <xsl:element name="places">
            <xsl:call-template name="getPlace">
                <xsl:with-param name="link" select="@link" />
            </xsl:call-template>
        </xsl:element>
    </xsl:template>

    <xsl:template name="getPlace">
        <xsl:param name="link" select="'Default'"/>
        <xsl:variable name="doc" select="document($link)"/>
        <xsl:for-each select="$doc//div[@class='col-md-3']">
            <xsl:element name="place">
                <xsl:element name="name">
                    <xsl:value-of select=".//div[@class='wapfooter']/a"/>
                    <xsl:text> </xsl:text>
                </xsl:element>
                <xsl:element name="categoriesString">
                    <xsl:text>Nhà hàng, Cơm quê</xsl:text>
                    <xsl:text> </xsl:text>
                </xsl:element>
                <xsl:element name="fullAddress">
                    <xsl:value-of select=".//p[@class='text-address notranslate']"/>
                    <xsl:text> </xsl:text>
                </xsl:element>
                <xsl:element name="image">
                    <xsl:value-of select=".//img/@src"/>
                    <xsl:text> </xsl:text>
                </xsl:element>
<!--                <xsl:element name="link">
                    <xsl:value-of select=".//div[@class='wapfooter']/a/@href"/>
                </xsl:element>-->
            </xsl:element>
        </xsl:for-each>
        <xsl:apply-templates select="$doc//ul[@class='pagination']//li[a[@rel='next']][1]/a"/>
    </xsl:template>
    <xsl:template match="ul[@class='pagination']//li[a[@rel='next']][1]/a">
        <xsl:call-template name="getPlace">
            <xsl:with-param name="link" select="@href"/>
        </xsl:call-template>
    </xsl:template>
</xsl:stylesheet>
