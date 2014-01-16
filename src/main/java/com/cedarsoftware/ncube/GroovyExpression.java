package com.cedarsoftware.ncube;

import com.cedarsoftware.util.UniqueIdGenerator;

import java.util.Set;

/**
 * This class is used to hold Groovy Expressions.  This means that
 * the code can start without any class or method signatures.  For
 * example, an expression could look like this:<pre>
 *    1) input.resource.KEY == 10
 *    2) $([BU:'agr',state:'OH') * 1.045
 *    3) return input.state == 'OH' ? 1.0 : 2.0
 *    4) output.result = 'answer computed'; return 1.4
 *
 * </pre>
 * Of course, Java syntax can be used, however, there are many nice
 * short-hands you can use if you know the Groovy language additions.
 * For example, Maps can be accessed like this (the following 3 are
 * equivalent): <pre>
 *    1) input.get('BU')
 *    2) input['BU']
 *    3) input.BU
 * </pre>
 * There are variables available to you to access in your expression
 * supplied by ncube.  <b>input</b> which is the input coordinate Map
 * that was used to get to the the cell containing the expression.
 * <b>output</b> is a Map that your expression can write to.  This allows
 * the program calling into the ncube to get multiple return values, with
 * possible structure to each one (a graph return).  <b>ncube</b> is the
 * current ncube of the cell containing the expression.  <b>ncubeMgr</b>
 * is a reference to the NCubeManager class so that you can access other
 * ncubes. <b>stack</b> which is a List of StackEntry's where element 0 is
 * the StackEntry for the currently executing cell.  Element 1 would be the
 * cell that called into the current cell (if it was during the same execution
 * cycle).  The StackEntry contains the name of the cube as one field, and
 * the coordinate that called into this cell.
 *
 * @author John DeRegnaucourt
 * Copyright (c) 2012-2013, John DeRegnaucourt.  All rights reserved.
 */
public class GroovyExpression extends GroovyBase
{
    public GroovyExpression(String cmd)
    {
        super(cmd);
    }

    public String buildGroovy(String theirGroovy, String cubeName)
    {
        StringBuilder groovyCodeWithoutImportStatements = new StringBuilder();
        Set<String> imports = getImports(theirGroovy, groovyCodeWithoutImportStatements);
        StringBuilder groovy = new StringBuilder();
        String className = "NGrvExp" + fixClassName(cubeName) + UniqueIdGenerator.getUniqueId();
        for (String importLine : imports)
        {
            groovy.append(importLine);
            groovy.append('\n');
        }
        groovy.append("class ");
        groovy.append(className);
        groovy.append(" extends NCubeGroovyCell");
        groovy.append("\n{\n");
        groovy.append(className);
        groovy.append("(Map args)\n{\n");
        groovy.append("  super(args);\n");
        groovy.append("}\n\n");
        groovy.append("def run()\n{\n");
        groovy.append(groovyCodeWithoutImportStatements);
        groovy.append("  \n}\n}");
        return groovy.toString();
    }

}