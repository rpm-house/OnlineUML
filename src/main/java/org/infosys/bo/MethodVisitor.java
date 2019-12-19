package org.infosys.bo;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.MethodInvocation;

public class MethodVisitor extends ASTVisitor { 

    List<MethodDeclaration> methods = new ArrayList<MethodDeclaration>();

    @Override public boolean visit(MethodDeclaration node) { 
        methods.add(node); 
        return super.visit(node); } 

    public List<MethodDeclaration> getMethods() 
        { return methods; }

    List<MethodInvocation> methods1 = new ArrayList<MethodInvocation>();

    @Override public boolean visit(MethodInvocation node)
        { methods1.add(node); 
          return super.visit(node); } 

   public List<MethodInvocation> getMethods1() 
        { return methods1; } 
   } 


