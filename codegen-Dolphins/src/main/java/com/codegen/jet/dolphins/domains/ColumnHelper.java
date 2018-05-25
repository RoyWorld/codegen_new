package com.codegen.jet.dolphins.domains;

import com.codegen.jet.dolphins.typemapping.DatabaseDataTypesUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ColumnHelper {

    static Pattern three = Pattern.compile("(.*)\\((.*),(.*)\\)");
    static Pattern two = Pattern.compile("(.*)\\((.*)\\)");

    public static String[] removeHibernateValidatorSpecialTags(String str) {
        if (str == null || str.trim().length() == 0) return new String[]{};
        return str.trim().replaceAll("@", "").replaceAll("\\(.*?\\)", "").trim().split("\\s+");
    }

    /**
     * 得到JSR303 bean validation的验证表达式
     */
    public static String getHibernateValidatorExpression(Column c) {
        if (!c.isPk() && !c.isNullable()) {
            if (DatabaseDataTypesUtils.isString(c.getJavaType())) {
                return "@NotBlank " + getNotRequiredHibernateValidatorExpression(c);
            } else {
                return "@NotNull " + getNotRequiredHibernateValidatorExpression(c);
            }
        } else {
            return getNotRequiredHibernateValidatorExpression(c);
        }
    }

    public static String getNotRequiredHibernateValidatorExpression(Column c) {
        String result = "";
        if (c.getSqlName().indexOf("mail") >= 0) {
            result += "@Email ";
        }
        if (DatabaseDataTypesUtils.isString(c.getJavaType())) {
            result += String.format("@Length(max=%s)", c.getSize());
        }
        if (DatabaseDataTypesUtils.isIntegerNumber(c.getJavaType())) {
            String javaType = DatabaseDataTypesUtils.getPreferredJavaType(c.getSqlType(), c.getSize(), c.getDecimalDigits());
            if (javaType.toLowerCase().indexOf("short") >= 0) {
                result += " @Max(" + Short.MAX_VALUE + ")";
            } else if (javaType.toLowerCase().indexOf("byte") >= 0) {
                result += " @Max(" + Byte.MAX_VALUE + ")";
            }
        }
        return result.trim();
    }

    /**
     * 得到rapid validation的验证表达式
     */
    public static String getRapidValidation(Column c) {
        String result = "";
        if (c.getSqlName().indexOf("mail") >= 0) {
            result += "validate-email ";
        }
        if (DatabaseDataTypesUtils.isFloatNumber(c.getJavaType())) {
            result += "validate-number ";
        }
        if (DatabaseDataTypesUtils.isIntegerNumber(c.getJavaType())) {
            result += "validate-integer ";
            if (c.getJavaType().toLowerCase().indexOf("short") >= 0) {
                result += "max-value-" + Short.MAX_VALUE;
            } else if (c.getJavaType().toLowerCase().indexOf("integer") >= 0) {
                result += "max-value-" + Integer.MAX_VALUE;
            } else if (c.getJavaType().toLowerCase().indexOf("byte") >= 0) {
                result += "max-value-" + Byte.MAX_VALUE;
            }
        }
        return result;
    }

    public static List<Column.EnumMetaDada> string2EnumMetaData(String data) {
        if (data == null || data.trim().length() == 0) return new ArrayList();
        //enumAlias(enumKey,enumDesc),enumAlias(enumDesc)

        List<Column.EnumMetaDada> list = new ArrayList();
        Pattern p = Pattern.compile("\\w+\\(.*?\\)");
        Matcher m = p.matcher(data);
        while (m.find()) {
            String str = m.group();
            Matcher three_m = three.matcher(str);
            if (three_m.find()) {
                list.add(new Column.EnumMetaDada(three_m.group(1), three_m.group(2), three_m.group(3)));
                continue;
            }
            Matcher two_m = two.matcher(str);
            if (two_m.find()) {
                list.add(new Column.EnumMetaDada(two_m.group(1), two_m.group(1), two_m.group(2)));
                continue;
            }
            throw new IllegalArgumentException("error enumString format:" + data + " expected format:F(1,Female);M(0,Male) or F(Female);M(Male)");
        }
        return list;
    }
}
