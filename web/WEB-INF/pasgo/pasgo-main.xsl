<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" 
                xmlns:t="http://thanhnd.xml/pasgo.vn/"
                xmlns="http://thanhnd.xml/pasgo.vn/"
                version="1.0"
>
    <xsl:output method="xml" omit-xml-declaration="yes" indent="yes"/>
    <xsl:template match="t:places">
        <xsl:variable name="doc" select="document(@link)"/>
        <xsl:element name="places">
            <xsl:for-each select="$doc//h3[1]/a">
                <xsl:call-template name="getPlace">
                    <xsl:with-param name="link" select="@href" />
                </xsl:call-template>
            </xsl:for-each>
        </xsl:element>
    </xsl:template>

    <xsl:template name="getPlace">
        <xsl:param name="link" select="'Default'"/>
        <xsl:variable name="doc" select="document($link)"/>
        <xsl:for-each select="$doc//div[@class='col-md-3']">
            <xsl:element name="place">
                <xsl:element name="name">
                    <xsl:value-of select=".//div[@class='wapfooter']/a"/>
                </xsl:element>
                <xsl:element name="categoriesString">
                    <xsl:value-of select="$doc//h3[1]/a[@class='active']/text()"/>
                </xsl:element>
                <xsl:element name="fullAddress">
                    <xsl:value-of select=".//p[@class='text-address notranslate']"/>
                </xsl:element>
                <xsl:element name="image">
                    <xsl:variable name="data-src" select=".//img/@data-src"/>
                    <xsl:variable name="src" select=".//img/@src"/>
                    <xsl:choose>
                        <xsl:when test="$data-src != ''">
                            <xsl:value-of select="$data-src" />
                        </xsl:when>
                        <xsl:otherwise>
                            <xsl:value-of select="$src" />
                        </xsl:otherwise>
                    </xsl:choose>
                </xsl:element>
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
