package com.cedarsoftware.util;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Calendar;
import java.util.Date;

/**
 * Handy conversion utilities
 *
 * @author John DeRegnaucourt (jdereg@gmail.com)
 *         <br/>
 *         Copyright (c) Cedar Software LLC
 *         <br/><br/>
 *         Licensed under the Apache License, Version 2.0 (the "License");
 *         you may not use this file except in compliance with the License.
 *         You may obtain a copy of the License at
 *         <br/><br/>
 *         http://www.apache.org/licenses/LICENSE-2.0
 *         <br/><br/>
 *         Unless required by applicable law or agreed to in writing, software
 *         distributed under the License is distributed on an "AS IS" BASIS,
 *         WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *         See the License for the specific language governing permissions and
 *         limitations under the License.
 */
public final class Converter
{
    /**
     * Turn the passed in value to the class indicated.  This will allow, for
     * example, a String value to be passed in and have it coerced to a Long.
     * <pre>
     *     Examples:
     *     Long x = convert("35", Long.class);
     *     Date d = convert("2015/01/01", Date.class)
     *     int y = convert(45.0, int.class)
     *     String date = convert(date, String.class)
     *     String date = convert(calendar, String.class)
     *     Short t = convert(true, short.class);     // returns (short) 1 or  (short) 0
     *     Long date = convert(calendar, long.class); // get calendar's time into long
     * </pre>
     * @param fromInstance A value used to create the targetType, even though it may
     * not (most likely will not) be the same data type as the targetType
     * @param toType Class which indicates the targeted (final) data type.
     * Please note that in addition to the 8 Java primitives, the targeted class
     * can also be Date.class, String.class, BigInteger.class, and BigDecimal.class.
     * The primitive class can be either primitive class or primitive wrapper class,
     * however, the returned value will always [obviously] be a primitive wrapper.
     * @return An instanceof targetType class, based upon the value passed in.
     */
    public static Object convert(Object fromInstance, Class toType)
    {
        switch(toType.getName())
        {
            case "byte":
            case "java.lang.Byte":
                try
                {
                    if (fromInstance instanceof Byte)
                    {
                        return fromInstance;
                    }
                    else if (fromInstance instanceof Number)
                    {
                        return ((Number)fromInstance).byteValue();
                    }
                    else if (fromInstance instanceof String)
                    {
                        return Byte.valueOf(((String) fromInstance).trim());
                    }
                    else if (fromInstance instanceof Boolean)
                    {
                        return (Boolean) fromInstance ? (byte) 1 : (byte) 0;
                    }
                }
                catch(Exception e)
                {
                    throw new IllegalArgumentException("value [" + fromInstance.getClass().getName() + "] could not be converted to a 'Byte'", e);
                }
                throw new IllegalArgumentException("Unsupported value type [" + fromInstance.getClass().getName() + "] attempting to convert to 'Byte'");

            case "short":
            case "java.lang.Short":
                try
                {
                    if (fromInstance instanceof Short)
                    {
                        return fromInstance;
                    }
                    else if (fromInstance instanceof Number)
                    {
                        return ((Number)fromInstance).shortValue();
                    }
                    else if (fromInstance instanceof String)
                    {
                        return Short.valueOf(((String) fromInstance).trim());
                    }
                    else if (fromInstance instanceof Boolean)
                    {
                        return (Boolean) fromInstance ? (short) 1 : (short) 0;
                    }
                }
                catch(Exception e)
                {
                    throw new IllegalArgumentException("value [" + fromInstance.getClass().getName() + "] could not be converted to a 'Short'", e);
                }
                throw new IllegalArgumentException("Unsupported value type [" + fromInstance.getClass().getName() + "] attempting to convert to 'Short'");

            case "int":
            case "java.lang.Integer":
                try
                {
                    if (fromInstance instanceof Integer)
                    {
                        return fromInstance;
                    }
                    else if (fromInstance instanceof Number)
                    {
                        return ((Number)fromInstance).intValue();
                    }
                    else if (fromInstance instanceof String)
                    {
                        return Integer.valueOf(((String) fromInstance).trim());
                    }
                    else if (fromInstance instanceof Boolean)
                    {
                        return (Boolean) fromInstance ? 1 : 0;
                    }
                }
                catch(Exception e)
                {
                    throw new IllegalArgumentException("value [" + fromInstance.getClass().getName() + "] could not be converted to an 'Integer'", e);
                }
                throw new IllegalArgumentException("Unsupported value type [" + fromInstance.getClass().getName() + "] attempting to convert to 'Integer'");

            case "long":
            case "java.lang.Long":
                try
                {
                    if (fromInstance instanceof Long)
                    {
                        return fromInstance;
                    }
                    else if (fromInstance instanceof Number)
                    {
                        return ((Number)fromInstance).longValue();
                    }
                    else if (fromInstance instanceof String)
                    {
                        return Long.valueOf(((String) fromInstance).trim());
                    }
                    else if (fromInstance instanceof Date)
                    {
                        return ((Date)fromInstance).getTime();
                    }
                    else if (fromInstance instanceof Boolean)
                    {
                        return (Boolean) fromInstance ? 1L : 0L;
                    }
                    else if (fromInstance instanceof Calendar)
                    {
                        return ((Calendar)fromInstance).getTime().getTime();
                    }
                }
                catch(Exception e)
                {
                    throw new IllegalArgumentException("value [" + fromInstance.getClass().getName() + "] could not be converted to a 'Long'", e);
                }
                throw new IllegalArgumentException("Unsupported value type [" + fromInstance.getClass().getName() + "] attempting to convert to 'Long'");

            case "java.lang.String":
                if (fromInstance instanceof String)
                {
                    return fromInstance;
                }
                else if (fromInstance instanceof BigDecimal)
                {
                    return ((BigDecimal) fromInstance).stripTrailingZeros().toPlainString();
                }
                else if (fromInstance instanceof Number || fromInstance instanceof Boolean)
                {
                    return fromInstance.toString();
                }
                else if (fromInstance instanceof Date)
                {
                    return SafeSimpleDateFormat.getDateFormat("yyyy-MM-dd'T'HH:mm:ss").format(fromInstance);
                }
                else if (fromInstance instanceof Calendar)
                {
                    return SafeSimpleDateFormat.getDateFormat("yyyy-MM-dd'T'HH:mm:ss").format(((Calendar)fromInstance).getTime());
                }
                else if (fromInstance instanceof Character)
                {
                    return "" + fromInstance;
                }
                throw new IllegalArgumentException("Unsupported value type [" + fromInstance.getClass().getName() + "] attempting to convert to 'String'");

            case "java.math.BigDecimal":
                try
                {
                    if (fromInstance instanceof BigDecimal)
                    {
                        return fromInstance;
                    }
                    else if (fromInstance instanceof BigInteger)
                    {
                        return new BigDecimal((BigInteger) fromInstance);
                    }
                    else if (fromInstance instanceof String)
                    {
                        return new BigDecimal(((String) fromInstance).trim());
                    }
                    else if (fromInstance instanceof Number)
                    {
                        return new BigDecimal(((Number) fromInstance).doubleValue());
                    }
                    else if (fromInstance instanceof Boolean)
                    {
                        return (Boolean) fromInstance ? BigDecimal.ONE : BigDecimal.ZERO;
                    }
                    else if (fromInstance instanceof Date)
                    {
                        return new BigDecimal(((Date)fromInstance).getTime());
                    }
                    else if (fromInstance instanceof Calendar)
                    {
                        return new BigDecimal(((Calendar)fromInstance).getTime().getTime());
                    }
                }
                catch(Exception e)
                {
                    throw new IllegalArgumentException("value [" + fromInstance.getClass().getName() + "] could not be converted to a 'BigDecimal'", e);
                }
                throw new IllegalArgumentException("Unsupported value type [" + fromInstance.getClass().getName() + "] attempting to convert to 'BigDecimal'");

            case "java.math.BigInteger":
                try
                {
                    if (fromInstance instanceof BigInteger)
                    {
                        return fromInstance;
                    }
                    else if (fromInstance instanceof BigDecimal)
                    {
                        return ((BigDecimal) fromInstance).toBigInteger();
                    }
                    else if (fromInstance instanceof String)
                    {
                        return new BigInteger(((String) fromInstance).trim());
                    }
                    else if (fromInstance instanceof Number)
                    {
                        return new BigInteger(Long.toString(((Number) fromInstance).longValue()));
                    }
                    else if (fromInstance instanceof Boolean)
                    {
                        return (Boolean) fromInstance ? BigInteger.ONE : BigInteger.ZERO;
                    }
                    else if (fromInstance instanceof Date)
                    {
                        return new BigInteger(Long.toString(((Date) fromInstance).getTime()));
                    }
                    else if (fromInstance instanceof Calendar)
                    {
                        return new BigInteger(Long.toString(((Calendar) fromInstance).getTime().getTime()));
                    }
                }
                catch(Exception e)
                {
                    throw new IllegalArgumentException("value [" + fromInstance.getClass().getName() + "] could not be converted to a 'BigInteger'", e);
                }
                throw new IllegalArgumentException("Unsupported value type [" + fromInstance.getClass().getName() + "] attempting to convert to 'BigInteger'");

            case "java.util.Date":
                try
                {
                    if (fromInstance instanceof java.sql.Date)
                    {   // convert from java.sql.Date to java.util.Date
                        return new Date(((java.sql.Date)fromInstance).getTime());
                    }
                    else if (fromInstance instanceof Date)
                    {
                        return fromInstance;
                    }
                    else if (fromInstance instanceof String)
                    {
                        return DateUtilities.parseDate(((String) fromInstance).trim());
                    }
                    else if (fromInstance instanceof Calendar)
                    {
                        return ((Calendar) fromInstance).getTime();
                    }
                    else if (fromInstance instanceof Long)
                    {
                        return new Date((Long) fromInstance);
                    }
                    throw new IllegalArgumentException("Unsupported value type [" + fromInstance.getClass().getName() + "] attempting to convert to 'Date'");
                }
                catch(Exception e)
                {
                    throw new IllegalArgumentException("value [" + fromInstance.getClass().getName() + "] could not be converted to a 'Date'", e);
                }

            case "java.sql.Date":
                try
                {
                    if (fromInstance instanceof java.sql.Date)
                    {
                        return fromInstance;
                    }
                    else if (fromInstance instanceof Date)
                    {   // convert from java.util.Date to java.sql.Date
                        return new java.sql.Date(((Date)fromInstance).getTime());
                    }
                    else if (fromInstance instanceof String)
                    {
                        Date date = DateUtilities.parseDate(((String) fromInstance).trim());
                        return new java.sql.Date(date.getTime());
                    }
                    else if (fromInstance instanceof Calendar)
                    {
                        return new java.sql.Date(((Calendar) fromInstance).getTime().getTime());
                    }
                    else if (fromInstance instanceof Long)
                    {
                        return new java.sql.Date((Long) fromInstance);
                    }
                    throw new IllegalArgumentException("Unsupported value type [" + fromInstance.getClass().getName() + "] attempting to convert to 'java.sql.Date'");
                }
                catch(Exception e)
                {
                    throw new IllegalArgumentException("value [" + fromInstance.getClass().getName() + "] could not be converted to a 'java.sql.Date'", e);
                }

            case "float":
            case "java.lang.Float":
                try
                {
                    if (fromInstance instanceof Float)
                    {
                        return fromInstance;
                    }
                    else if (fromInstance instanceof Number)
                    {
                        return ((Number)fromInstance).floatValue();
                    }
                    else if (fromInstance instanceof String)
                    {
                        return Float.valueOf(((String) fromInstance).trim());
                    }
                    else if (fromInstance instanceof Boolean)
                    {
                        return (Boolean) fromInstance ? 1.0f : 0.0f;
                    }
                }
                catch(Exception e)
                {
                    throw new IllegalArgumentException("value [" + fromInstance.getClass().getName() + "] could not be converted to a 'Float'", e);
                }
                throw new IllegalArgumentException("Unsupported value type [" + fromInstance.getClass().getName() + "] attempting to convert to 'Float'");

            case "double":
            case "java.lang.Double":
                try
                {
                    if (fromInstance instanceof Double)
                    {
                        return fromInstance;
                    }
                    else if (fromInstance instanceof Number)
                    {
                        return ((Number)fromInstance).doubleValue();
                    }
                    else if (fromInstance instanceof String)
                    {
                        return Double.valueOf(((String) fromInstance).trim());
                    }
                    else if (fromInstance instanceof Boolean)
                    {
                        return (Boolean) fromInstance ? 1.0d : 0.0d;
                    }
                }
                catch(Exception e)
                {
                    throw new IllegalArgumentException("value [" + fromInstance.getClass().getName() + "] could not be converted to a 'Double'", e);
                }
                throw new IllegalArgumentException("Unsupported value type [" + fromInstance.getClass().getName() + "] attempting to convert to 'Double'");

        }
        throw new IllegalArgumentException("Unsupported type '" + toType.getName() + "' for conversion");
    }
}