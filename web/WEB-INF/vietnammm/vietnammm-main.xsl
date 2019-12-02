<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns:t="http://thanhnd.xml/vietnammm.com/"
                xmlns="http://thanhnd.xml/vietnammm.com/"
                version="1.0"
>
    <xsl:output method="xml" omit-xml-declaration="yes" indent="yes"/>
    <xsl:template match="t:places">
        <xsl:element name="places">
            <xsl:variable name="doc" select="document(@link)"/>
            <xsl:variable name="host" select="@host"/>
            <xsl:for-each select="$doc//div[@class='restaurant grid' and @id != 'SingleRestaurantTemplateIdentifier']">
                <xsl:element name="place">
                    <xsl:element name="name">
                        <xsl:value-of select=".//a[@class='restaurantname']"/>
                    </xsl:element>
                    <xsl:element name="categoriesString">
                        <xsl:value-of select=".//div[@class='kitchens']/span"/>
                    </xsl:element>
                    <xsl:element name="fullAddress">
                        <xsl:call-template name="getAddress">
                            <xsl:with-param name="link" select="concat($host , .//a[@class='img-link']/@href)"/>
                            <xsl:with-param name="host" select="$host"/>
                        </xsl:call-template>
                    </xsl:element>
                    <xsl:element name="image">
                        <xsl:value-of select=".//img[@class='restlogo'][1]/@data-original"/>
                    </xsl:element>
                </xsl:element>
            </xsl:for-each>
        </xsl:element>
    </xsl:template>

    <xsl:template name="getAddress">
        <xsl:param name="link" select="'Default'"/>
        <xsl:param name="host" select="'Default'"/>
        <xsl:variable name="docDetail" select="document($link)"/>
        <xsl:call-template name="getAddressValue">
            <xsl:with-param name="link" select="concat($host , $docDetail//li[@id='tab_MoreInfo']/a/@href)"/>
            <xsl:with-param name="host" select="$host"/>
        </xsl:call-template>
    </xsl:template>

    <xsl:template name="getAddressValue">
        <xsl:param name="link" select="'Default'"/>
        <xsl:param name="host" select="'Default'"/>
        <xsl:variable name="docMoreInfo" select="document($link)"/>
        <xsl:value-of select="$docMoreInfo//div[@id='infoMapWrapper']//section[@class='card-body']"/>
    </xsl:template>
</xsl:stylesheet>
