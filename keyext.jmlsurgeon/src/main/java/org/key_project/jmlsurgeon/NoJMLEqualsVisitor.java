//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package org.key_project.jmlsurgeon;

import com.github.javaparser.ast.*;
import com.github.javaparser.ast.body.AnnotationDeclaration;
import com.github.javaparser.ast.body.AnnotationMemberDeclaration;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.CompactConstructorDeclaration;
import com.github.javaparser.ast.body.ConstructorDeclaration;
import com.github.javaparser.ast.body.EnumConstantDeclaration;
import com.github.javaparser.ast.body.EnumDeclaration;
import com.github.javaparser.ast.body.FieldDeclaration;
import com.github.javaparser.ast.body.InitializerDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.body.Parameter;
import com.github.javaparser.ast.body.ReceiverParameter;
import com.github.javaparser.ast.body.RecordDeclaration;
import com.github.javaparser.ast.body.VariableDeclarator;
import com.github.javaparser.ast.comments.BlockComment;
import com.github.javaparser.ast.comments.JavadocComment;
import com.github.javaparser.ast.comments.LineComment;
import com.github.javaparser.ast.expr.*;
import com.github.javaparser.ast.jml.body.JmlClassAccessibleDeclaration;
import com.github.javaparser.ast.jml.body.JmlClassExprDeclaration;
import com.github.javaparser.ast.jml.body.JmlFieldDeclaration;
import com.github.javaparser.ast.jml.body.JmlMethodDeclaration;
import com.github.javaparser.ast.jml.body.JmlRepresentsDeclaration;
import com.github.javaparser.ast.jml.clauses.JmlCallableClause;
import com.github.javaparser.ast.jml.clauses.JmlClauseIf;
import com.github.javaparser.ast.jml.clauses.JmlClauseLabel;
import com.github.javaparser.ast.jml.clauses.JmlContract;
import com.github.javaparser.ast.jml.clauses.JmlForallClause;
import com.github.javaparser.ast.jml.clauses.JmlMethodSignature;
import com.github.javaparser.ast.jml.clauses.JmlMultiExprClause;
import com.github.javaparser.ast.jml.clauses.JmlOldClause;
import com.github.javaparser.ast.jml.clauses.JmlSignalsClause;
import com.github.javaparser.ast.jml.clauses.JmlSignalsOnlyClause;
import com.github.javaparser.ast.jml.clauses.JmlSimpleExprClause;
import com.github.javaparser.ast.jml.doc.*;
import com.github.javaparser.ast.jml.expr.JmlBinaryInfixExpr;
import com.github.javaparser.ast.jml.expr.JmlLabelExpr;
import com.github.javaparser.ast.jml.expr.JmlLetExpr;
import com.github.javaparser.ast.jml.expr.JmlMultiCompareExpr;
import com.github.javaparser.ast.jml.expr.JmlQuantifiedExpr;
import com.github.javaparser.ast.jml.expr.JmlSetComprehensionExpr;
import com.github.javaparser.ast.jml.expr.JmlTypeExpr;
import com.github.javaparser.ast.jml.stmt.JmlBeginStmt;
import com.github.javaparser.ast.jml.stmt.JmlEndStmt;
import com.github.javaparser.ast.jml.stmt.JmlExpressionStmt;
import com.github.javaparser.ast.jml.stmt.JmlGhostStmt;
import com.github.javaparser.ast.jml.stmt.JmlLabelStmt;
import com.github.javaparser.ast.jml.stmt.JmlRefiningStmt;
import com.github.javaparser.ast.jml.stmt.JmlUnreachableStmt;
import com.github.javaparser.ast.modules.ModuleDeclaration;
import com.github.javaparser.ast.modules.ModuleExportsDirective;
import com.github.javaparser.ast.modules.ModuleOpensDirective;
import com.github.javaparser.ast.modules.ModuleProvidesDirective;
import com.github.javaparser.ast.modules.ModuleRequiresDirective;
import com.github.javaparser.ast.modules.ModuleUsesDirective;
import com.github.javaparser.ast.stmt.AssertStmt;
import com.github.javaparser.ast.stmt.BlockStmt;
import com.github.javaparser.ast.stmt.BreakStmt;
import com.github.javaparser.ast.stmt.CatchClause;
import com.github.javaparser.ast.stmt.ContinueStmt;
import com.github.javaparser.ast.stmt.DoStmt;
import com.github.javaparser.ast.stmt.EmptyStmt;
import com.github.javaparser.ast.stmt.ExplicitConstructorInvocationStmt;
import com.github.javaparser.ast.stmt.ExpressionStmt;
import com.github.javaparser.ast.stmt.ForEachStmt;
import com.github.javaparser.ast.stmt.ForStmt;
import com.github.javaparser.ast.stmt.IfStmt;
import com.github.javaparser.ast.stmt.LabeledStmt;
import com.github.javaparser.ast.stmt.LocalClassDeclarationStmt;
import com.github.javaparser.ast.stmt.LocalRecordDeclarationStmt;
import com.github.javaparser.ast.stmt.ReturnStmt;
import com.github.javaparser.ast.stmt.SwitchEntry;
import com.github.javaparser.ast.stmt.SwitchStmt;
import com.github.javaparser.ast.stmt.SynchronizedStmt;
import com.github.javaparser.ast.stmt.ThrowStmt;
import com.github.javaparser.ast.stmt.TryStmt;
import com.github.javaparser.ast.stmt.UnparsableStmt;
import com.github.javaparser.ast.stmt.WhileStmt;
import com.github.javaparser.ast.stmt.YieldStmt;
import com.github.javaparser.ast.type.ArrayType;
import com.github.javaparser.ast.type.ClassOrInterfaceType;
import com.github.javaparser.ast.type.IntersectionType;
import com.github.javaparser.ast.type.PrimitiveType;
import com.github.javaparser.ast.type.TypeParameter;
import com.github.javaparser.ast.type.UnionType;
import com.github.javaparser.ast.type.UnknownType;
import com.github.javaparser.ast.type.VarType;
import com.github.javaparser.ast.type.VoidType;
import com.github.javaparser.ast.type.WildcardType;
import com.github.javaparser.ast.visitor.GenericVisitor;
import com.github.javaparser.ast.visitor.Visitable;

import java.util.List;
import java.util.Optional;

public class NoJMLEqualsVisitor implements GenericVisitor<Boolean, Visitable> {
    private Node offendingNode;

    public static Node equals(final Node n, final Node n2) {
        NoJMLEqualsVisitor noJMLEqualsVisitor = new NoJMLEqualsVisitor();
        noJMLEqualsVisitor.nodeEquals(n, n2);
        return noJMLEqualsVisitor.offendingNode;
    }

    private NoJMLEqualsVisitor() {
    }

    private boolean commonNodeEquality(Node n, Node n2) {
        return this.nodeEquals(n.getComment(), n2.getComment()) && this.nodesEquals(n.getOrphanComments(), n2.getOrphanComments());
    }

    private <T extends Node> boolean nodesEquals(final List<T> nodes1, final List<T> nodes2) {
        if (nodes1 == null) {
            return nodes2 == null;
        } else if (nodes2 == null) {
            return false;
        } else if (nodes1.size() != nodes2.size()) {
            return false;
        } else {
            for(int i = 0; i < nodes1.size(); ++i) {
                if (!this.nodeEquals((Node)nodes1.get(i), (Node)nodes2.get(i))) {
                    return false;
                }
            }
            return true;
        }
    }

    private <N extends Node> boolean nodesEquals(NodeList<N> n, NodeList<N> n2) {
        if (n == n2) {
            return true;
        } else if (n != null && n2 != null) {
            n.removeIf(node -> (node instanceof Jmlish || node instanceof JmlDocContainer));
            n2.removeIf(node -> (node instanceof Jmlish || node instanceof JmlDocContainer));
            if (n.size() != n2.size()) {
                return false;
            } else {
                for(int i = 0; i < n.size(); ++i) {
                    if (!this.nodeEquals(n.get(i), n2.get(i))) {
                        return false;
                    }
                }

                return true;
            }
        } else {
            return false;
        }
    }

    private <T extends Node> boolean nodeEquals(final T n, final T n2) {
        if (n == n2) {
            return true;
        } else if (n != null && n2 != null) {
            if (n.getClass() != n2.getClass()) {
                if (n instanceof EnclosedExpr || n2 instanceof EnclosedExpr) {
                    if  (n instanceof EnclosedExpr) {
                        return this.nodeEquals(((EnclosedExpr)n).getInner(), n2);
                    } else {
                        return this.nodeEquals(n, ((EnclosedExpr) n2).getInner());
                    }
                }
                //Needs an exception to account for BlockStmt wrapping JML and another statement
                BlockStmt block;
                Node other;
                if (n instanceof  BlockStmt) {
                    block = (BlockStmt) n;
                    other = n2;
                } else if (n2 instanceof BlockStmt) {
                    block = (BlockStmt) n2;
                    other = n;
                } else {
                    offendingNode = n2;
                    return false;
                }

                List<Node> nonJmlNodes = block.getChildNodes().stream().filter((child) -> !(child instanceof Jmlish || child instanceof JmlDocContainer)).toList();
                if (nonJmlNodes.size() != 1) {
                    offendingNode = n2;
                    return false;
                }
                return this.nodeEquals(nonJmlNodes.getFirst(), other);
            } else {
                return this.commonNodeEquality(n, n2) && n.accept(this, n2);
            }
        } else {
            offendingNode = n2;
            return false;
        }
    }

    private <T extends Node> boolean nodeEquals(final Optional<T> n, final Optional<T> n2) {
        return this.nodeEquals(n.orElse(null), n2.orElse(null));
    }

    private <T extends Node> boolean nodesEquals(final Optional<NodeList<T>> n, final Optional<NodeList<T>> n2) {
        return this.nodesEquals(n.orElse(null), n2.orElse(null));
    }

    private boolean objEquals(final Object n, final Object n2) {
        if (n == n2) {
            return true;
        } else {
            return n != null && n.equals(n2);
        }
    }

    public Boolean visit(final CompilationUnit n, final Visitable arg) {
        CompilationUnit n2 = (CompilationUnit)arg;
        if (!this.nodesEquals(n.getImports(), n2.getImports())) {
            return false;
        } else if (!this.nodeEquals(n.getModule(), n2.getModule())) {
            return false;
        } else if (!this.nodeEquals(n.getPackageDeclaration(), n2.getPackageDeclaration())) {
            return false;
        } else if (!this.nodesEquals(n.getTypes(), n2.getTypes())) {
            return false;
        } else {
            return this.nodeEquals(n.getComment(), n2.getComment());
        }
    }

    public Boolean visit(final PackageDeclaration n, final Visitable arg) {
        PackageDeclaration n2 = (PackageDeclaration)arg;
        if (!this.nodesEquals(n.getAnnotations(), n2.getAnnotations())) {
            return false;
        } else if (!this.nodeEquals(n.getName(), n2.getName())) {
            return false;
        } else {
            return this.nodeEquals(n.getComment(), n2.getComment());
        }
    }

    public Boolean visit(final TypeParameter n, final Visitable arg) {
        TypeParameter n2 = (TypeParameter)arg;
        if (!this.nodeEquals(n.getName(), n2.getName())) {
            return false;
        } else if (!this.nodesEquals(n.getTypeBound(), n2.getTypeBound())) {
            return false;
        } else if (!this.nodesEquals(n.getAnnotations(), n2.getAnnotations())) {
            return false;
        } else {
            return this.nodeEquals(n.getComment(), n2.getComment());
        }
    }

    public Boolean visit(final LineComment n, final Visitable arg) {
        LineComment n2 = (LineComment)arg;
        if (!this.objEquals(n.getContent(), n2.getContent())) {
            return false;
        } else {
            return this.nodeEquals(n.getComment(), n2.getComment());
        }
    }

    public Boolean visit(final BlockComment n, final Visitable arg) {
        BlockComment n2 = (BlockComment)arg;
        if (!this.objEquals(n.getContent(), n2.getContent())) {
            return false;
        } else {
            return !this.nodeEquals(n.getComment(), n2.getComment()) ? false : true;
        }
    }

    public Boolean visit(final ClassOrInterfaceDeclaration n, final Visitable arg) {
        ClassOrInterfaceDeclaration n2 = (ClassOrInterfaceDeclaration)arg;
        if (!this.nodesEquals(n.getExtendedTypes(), n2.getExtendedTypes())) {
            return false;
        } else if (!this.nodesEquals(n.getImplementedTypes(), n2.getImplementedTypes())) {
            return false;
        } else if (!this.objEquals(n.isInterface(), n2.isInterface())) {
            return false;
        } else if (!this.nodesEquals(n.getPermittedTypes(), n2.getPermittedTypes())) {
            return false;
        } else if (!this.nodesEquals(n.getTypeParameters(), n2.getTypeParameters())) {
            return false;
        } else if (!this.nodesEquals(n.getMembers(), n2.getMembers())) {
            return false;
        } else if (!this.nodesEquals(n.getModifiers(), n2.getModifiers())) {
            return false;
        } else if (!this.nodeEquals(n.getName(), n2.getName())) {
            return false;
        } else if (!this.nodesEquals(n.getAnnotations(), n2.getAnnotations())) {
            return false;
        } else {
            return !this.nodeEquals(n.getComment(), n2.getComment()) ? false : true;
        }
    }

    public Boolean visit(final EnumDeclaration n, final Visitable arg) {
        EnumDeclaration n2 = (EnumDeclaration)arg;
        if (!this.nodesEquals(n.getEntries(), n2.getEntries())) {
            return false;
        } else if (!this.nodesEquals(n.getImplementedTypes(), n2.getImplementedTypes())) {
            return false;
        } else if (!this.nodesEquals(n.getMembers(), n2.getMembers())) {
            return false;
        } else if (!this.nodesEquals(n.getModifiers(), n2.getModifiers())) {
            return false;
        } else if (!this.nodeEquals(n.getName(), n2.getName())) {
            return false;
        } else if (!this.nodesEquals(n.getAnnotations(), n2.getAnnotations())) {
            return false;
        } else {
            return !this.nodeEquals(n.getComment(), n2.getComment()) ? false : true;
        }
    }

    public Boolean visit(final EnumConstantDeclaration n, final Visitable arg) {
        EnumConstantDeclaration n2 = (EnumConstantDeclaration)arg;
        if (!this.nodesEquals(n.getArguments(), n2.getArguments())) {
            return false;
        } else if (!this.nodesEquals(n.getClassBody(), n2.getClassBody())) {
            return false;
        } else if (!this.nodeEquals(n.getName(), n2.getName())) {
            return false;
        } else if (!this.nodesEquals(n.getAnnotations(), n2.getAnnotations())) {
            return false;
        } else {
            return !this.nodeEquals(n.getComment(), n2.getComment()) ? false : true;
        }
    }

    public Boolean visit(final AnnotationDeclaration n, final Visitable arg) {
        AnnotationDeclaration n2 = (AnnotationDeclaration)arg;
        if (!this.nodesEquals(n.getMembers(), n2.getMembers())) {
            return false;
        } else if (!this.nodesEquals(n.getModifiers(), n2.getModifiers())) {
            return false;
        } else if (!this.nodeEquals(n.getName(), n2.getName())) {
            return false;
        } else if (!this.nodesEquals(n.getAnnotations(), n2.getAnnotations())) {
            return false;
        } else {
            return !this.nodeEquals(n.getComment(), n2.getComment()) ? false : true;
        }
    }

    public Boolean visit(final AnnotationMemberDeclaration n, final Visitable arg) {
        AnnotationMemberDeclaration n2 = (AnnotationMemberDeclaration)arg;
        if (!this.nodeEquals(n.getDefaultValue(), n2.getDefaultValue())) {
            return false;
        } else if (!this.nodesEquals(n.getModifiers(), n2.getModifiers())) {
            return false;
        } else if (!this.nodeEquals(n.getName(), n2.getName())) {
            return false;
        } else if (!this.nodeEquals(n.getType(), n2.getType())) {
            return false;
        } else if (!this.nodesEquals(n.getAnnotations(), n2.getAnnotations())) {
            return false;
        } else {
            return !this.nodeEquals(n.getComment(), n2.getComment()) ? false : true;
        }
    }

    public Boolean visit(final FieldDeclaration n, final Visitable arg) {
        FieldDeclaration n2 = (FieldDeclaration)arg;
        if (!this.nodesEquals(n.getModifiers(), n2.getModifiers())) {
            return false;
        } else if (!this.nodesEquals(n.getVariables(), n2.getVariables())) {
            return false;
        } else if (!this.nodesEquals(n.getAnnotations(), n2.getAnnotations())) {
            return false;
        } else {
            return !this.nodeEquals(n.getComment(), n2.getComment()) ? false : true;
        }
    }

    public Boolean visit(final VariableDeclarator n, final Visitable arg) {
        VariableDeclarator n2 = (VariableDeclarator)arg;
        if (!this.nodeEquals(n.getInitializer(), n2.getInitializer())) {
            return false;
        } else if (!this.nodeEquals(n.getName(), n2.getName())) {
            return false;
        } else if (!this.nodeEquals(n.getType(), n2.getType())) {
            return false;
        } else {
            return !this.nodeEquals(n.getComment(), n2.getComment()) ? false : true;
        }
    }

    public Boolean visit(final ConstructorDeclaration n, final Visitable arg) {
        ConstructorDeclaration n2 = (ConstructorDeclaration)arg;
        if (!this.nodeEquals(n.getBody(), n2.getBody())) {
            return false;
        } else if (!this.nodesEquals(n.getContracts(), n2.getContracts())) {
            return false;
        } else if (!this.nodesEquals(n.getModifiers(), n2.getModifiers())) {
            return false;
        } else if (!this.nodeEquals(n.getName(), n2.getName())) {
            return false;
        } else if (!this.nodesEquals(n.getParameters(), n2.getParameters())) {
            return false;
        } else if (!this.nodeEquals(n.getReceiverParameter(), n2.getReceiverParameter())) {
            return false;
        } else if (!this.nodesEquals(n.getThrownExceptions(), n2.getThrownExceptions())) {
            return false;
        } else if (!this.nodesEquals(n.getTypeParameters(), n2.getTypeParameters())) {
            return false;
        } else if (!this.nodesEquals(n.getAnnotations(), n2.getAnnotations())) {
            return false;
        } else {
            return !this.nodeEquals(n.getComment(), n2.getComment()) ? false : true;
        }
    }

    public Boolean visit(final MethodDeclaration n, final Visitable arg) {
        MethodDeclaration n2 = (MethodDeclaration)arg;
        if (!this.nodeEquals(n.getBody(), n2.getBody())) {
            return false;
        } else if (!this.nodeEquals(n.getType(), n2.getType())) {
            return false;
        } else if (!this.nodesEquals(n.getContracts(), n2.getContracts())) {
            return false;
        } else if (!this.nodesEquals(n.getModifiers(), n2.getModifiers())) {
            return false;
        } else if (!this.nodeEquals(n.getName(), n2.getName())) {
            return false;
        } else if (!this.nodesEquals(n.getParameters(), n2.getParameters())) {
            return false;
        } else if (!this.nodeEquals(n.getReceiverParameter(), n2.getReceiverParameter())) {
            return false;
        } else if (!this.nodesEquals(n.getThrownExceptions(), n2.getThrownExceptions())) {
            return false;
        } else if (!this.nodesEquals(n.getTypeParameters(), n2.getTypeParameters())) {
            return false;
        } else if (!this.nodesEquals(n.getAnnotations(), n2.getAnnotations())) {
            return false;
        } else {
            return !this.nodeEquals(n.getComment(), n2.getComment()) ? false : true;
        }
    }

    public Boolean visit(final Parameter n, final Visitable arg) {
        Parameter n2 = (Parameter)arg;
        if (!this.nodesEquals(n.getAnnotations(), n2.getAnnotations())) {
            return false;
        } else if (!this.objEquals(n.isVarArgs(), n2.isVarArgs())) {
            return false;
        } else if (!this.nodesEquals(n.getModifiers(), n2.getModifiers())) {
            return false;
        } else if (!this.nodeEquals(n.getName(), n2.getName())) {
            return false;
        } else if (!this.nodeEquals(n.getType(), n2.getType())) {
            return false;
        } else if (!this.nodesEquals(n.getVarArgsAnnotations(), n2.getVarArgsAnnotations())) {
            return false;
        } else {
            return !this.nodeEquals(n.getComment(), n2.getComment()) ? false : true;
        }
    }

    public Boolean visit(final InitializerDeclaration n, final Visitable arg) {
        InitializerDeclaration n2 = (InitializerDeclaration)arg;
        if (!this.nodeEquals(n.getBody(), n2.getBody())) {
            return false;
        } else if (!this.objEquals(n.isStatic(), n2.isStatic())) {
            return false;
        } else if (!this.nodesEquals(n.getAnnotations(), n2.getAnnotations())) {
            return false;
        } else {
            return !this.nodeEquals(n.getComment(), n2.getComment()) ? false : true;
        }
    }

    public Boolean visit(final JavadocComment n, final Visitable arg) {
        JavadocComment n2 = (JavadocComment)arg;
        if (!this.objEquals(n.getContent(), n2.getContent())) {
            return false;
        } else {
            return !this.nodeEquals(n.getComment(), n2.getComment()) ? false : true;
        }
    }

    public Boolean visit(final ClassOrInterfaceType n, final Visitable arg) {
        ClassOrInterfaceType n2 = (ClassOrInterfaceType)arg;
        if (!this.nodeEquals(n.getName(), n2.getName())) {
            return false;
        } else if (!this.nodeEquals(n.getScope(), n2.getScope())) {
            return false;
        } else if (!this.nodesEquals(n.getTypeArguments(), n2.getTypeArguments())) {
            return false;
        } else if (!this.nodesEquals(n.getAnnotations(), n2.getAnnotations())) {
            return false;
        } else {
            return !this.nodeEquals(n.getComment(), n2.getComment()) ? false : true;
        }
    }

    public Boolean visit(final PrimitiveType n, final Visitable arg) {
        PrimitiveType n2 = (PrimitiveType)arg;
        if (!this.objEquals(n.getType(), n2.getType())) {
            return false;
        } else if (!this.nodesEquals(n.getAnnotations(), n2.getAnnotations())) {
            return false;
        } else {
            return !this.nodeEquals(n.getComment(), n2.getComment()) ? false : true;
        }
    }

    public Boolean visit(final ArrayType n, final Visitable arg) {
        ArrayType n2 = (ArrayType)arg;
        if (!this.nodeEquals(n.getComponentType(), n2.getComponentType())) {
            return false;
        } else if (!this.objEquals(n.getOrigin(), n2.getOrigin())) {
            return false;
        } else if (!this.nodesEquals(n.getAnnotations(), n2.getAnnotations())) {
            return false;
        } else {
            return !this.nodeEquals(n.getComment(), n2.getComment()) ? false : true;
        }
    }

    public Boolean visit(final ArrayCreationLevel n, final Visitable arg) {
        ArrayCreationLevel n2 = (ArrayCreationLevel)arg;
        if (!this.nodesEquals(n.getAnnotations(), n2.getAnnotations())) {
            return false;
        } else if (!this.nodeEquals(n.getDimension(), n2.getDimension())) {
            return false;
        } else {
            return !this.nodeEquals(n.getComment(), n2.getComment()) ? false : true;
        }
    }

    public Boolean visit(final IntersectionType n, final Visitable arg) {
        IntersectionType n2 = (IntersectionType)arg;
        if (!this.nodesEquals(n.getElements(), n2.getElements())) {
            return false;
        } else if (!this.nodesEquals(n.getAnnotations(), n2.getAnnotations())) {
            return false;
        } else {
            return !this.nodeEquals(n.getComment(), n2.getComment()) ? false : true;
        }
    }

    public Boolean visit(final UnionType n, final Visitable arg) {
        UnionType n2 = (UnionType)arg;
        if (!this.nodesEquals(n.getElements(), n2.getElements())) {
            return false;
        } else if (!this.nodesEquals(n.getAnnotations(), n2.getAnnotations())) {
            return false;
        } else {
            return !this.nodeEquals(n.getComment(), n2.getComment()) ? false : true;
        }
    }

    public Boolean visit(final VoidType n, final Visitable arg) {
        VoidType n2 = (VoidType)arg;
        if (!this.nodesEquals(n.getAnnotations(), n2.getAnnotations())) {
            return false;
        } else {
            return !this.nodeEquals(n.getComment(), n2.getComment()) ? false : true;
        }
    }

    public Boolean visit(final WildcardType n, final Visitable arg) {
        WildcardType n2 = (WildcardType)arg;
        if (!this.nodeEquals(n.getExtendedType(), n2.getExtendedType())) {
            return false;
        } else if (!this.nodeEquals(n.getSuperType(), n2.getSuperType())) {
            return false;
        } else if (!this.nodesEquals(n.getAnnotations(), n2.getAnnotations())) {
            return false;
        } else {
            return !this.nodeEquals(n.getComment(), n2.getComment()) ? false : true;
        }
    }

    public Boolean visit(final UnknownType n, final Visitable arg) {
        UnknownType n2 = (UnknownType)arg;
        if (!this.nodesEquals(n.getAnnotations(), n2.getAnnotations())) {
            return false;
        } else {
            return !this.nodeEquals(n.getComment(), n2.getComment()) ? false : true;
        }
    }

    public Boolean visit(final ArrayAccessExpr n, final Visitable arg) {
        ArrayAccessExpr n2 = (ArrayAccessExpr)arg;
        if (!this.nodeEquals(n.getIndex(), n2.getIndex())) {
            return false;
        } else if (!this.nodeEquals(n.getName(), n2.getName())) {
            return false;
        } else {
            return !this.nodeEquals(n.getComment(), n2.getComment()) ? false : true;
        }
    }

    public Boolean visit(final ArrayCreationExpr n, final Visitable arg) {
        ArrayCreationExpr n2 = (ArrayCreationExpr)arg;
        if (!this.nodeEquals(n.getElementType(), n2.getElementType())) {
            return false;
        } else if (!this.nodeEquals(n.getInitializer(), n2.getInitializer())) {
            return false;
        } else if (!this.nodesEquals(n.getLevels(), n2.getLevels())) {
            return false;
        } else {
            return !this.nodeEquals(n.getComment(), n2.getComment()) ? false : true;
        }
    }

    public Boolean visit(final ArrayInitializerExpr n, final Visitable arg) {
        ArrayInitializerExpr n2 = (ArrayInitializerExpr)arg;
        if (!this.nodesEquals(n.getValues(), n2.getValues())) {
            return false;
        } else {
            return !this.nodeEquals(n.getComment(), n2.getComment()) ? false : true;
        }
    }

    public Boolean visit(final AssignExpr n, final Visitable arg) {
        AssignExpr n2 = (AssignExpr)arg;
        if (!this.objEquals(n.getOperator(), n2.getOperator())) {
            return false;
        } else if (!this.nodeEquals(n.getTarget(), n2.getTarget())) {
            return false;
        } else if (!this.nodeEquals(n.getValue(), n2.getValue())) {
            return false;
        } else {
            return !this.nodeEquals(n.getComment(), n2.getComment()) ? false : true;
        }
    }

    public Boolean visit(final BinaryExpr n, final Visitable arg) {
        BinaryExpr n2 = (BinaryExpr)arg;
        if (!this.nodeEquals(n.getLeft(), n2.getLeft())) {
            return false;
        } else if (!this.objEquals(n.getOperator(), n2.getOperator())) {
            return false;
        } else if (!this.nodeEquals(n.getRight(), n2.getRight())) {
            return false;
        } else {
            return !this.nodeEquals(n.getComment(), n2.getComment()) ? false : true;
        }
    }

    public Boolean visit(final CastExpr n, final Visitable arg) {
        CastExpr n2 = (CastExpr)arg;
        if (!this.nodeEquals(n.getExpression(), n2.getExpression())) {
            return false;
        } else if (!this.nodeEquals(n.getType(), n2.getType())) {
            return false;
        } else {
            return !this.nodeEquals(n.getComment(), n2.getComment()) ? false : true;
        }
    }

    public Boolean visit(final ClassExpr n, final Visitable arg) {
        ClassExpr n2 = (ClassExpr)arg;
        if (!this.nodeEquals(n.getType(), n2.getType())) {
            return false;
        } else {
            return !this.nodeEquals(n.getComment(), n2.getComment()) ? false : true;
        }
    }

    public Boolean visit(final ConditionalExpr n, final Visitable arg) {
        ConditionalExpr n2 = (ConditionalExpr)arg;
        if (!this.nodeEquals(n.getCondition(), n2.getCondition())) {
            return false;
        } else if (!this.nodeEquals(n.getElseExpr(), n2.getElseExpr())) {
            return false;
        } else if (!this.nodeEquals(n.getThenExpr(), n2.getThenExpr())) {
            return false;
        } else {
            return !this.nodeEquals(n.getComment(), n2.getComment()) ? false : true;
        }
    }

    public Boolean visit(final EnclosedExpr n, final Visitable arg) {
        EnclosedExpr n2 = (EnclosedExpr)arg;
        if (!this.nodeEquals(n.getInner(), n2.getInner())) {
            return false;
        } else {
            return !this.nodeEquals(n.getComment(), n2.getComment()) ? false : true;
        }
    }

    public Boolean visit(final FieldAccessExpr n, final Visitable arg) {
        FieldAccessExpr n2 = (FieldAccessExpr)arg;
        if (!this.nodeEquals(n.getName(), n2.getName())) {
            return false;
        } else if (!this.nodeEquals(n.getScope(), n2.getScope())) {
            return false;
        } else if (!this.nodesEquals(n.getTypeArguments(), n2.getTypeArguments())) {
            return false;
        } else {
            return !this.nodeEquals(n.getComment(), n2.getComment()) ? false : true;
        }
    }

    public Boolean visit(final InstanceOfExpr n, final Visitable arg) {
        InstanceOfExpr n2 = (InstanceOfExpr)arg;
        if (!this.nodeEquals(n.getExpression(), n2.getExpression())) {
            return false;
        } else if (!this.nodeEquals(n.getPattern(), n2.getPattern())) {
            return false;
        } else if (!this.nodeEquals(n.getType(), n2.getType())) {
            return false;
        } else {
            return !this.nodeEquals(n.getComment(), n2.getComment()) ? false : true;
        }
    }

    public Boolean visit(final StringLiteralExpr n, final Visitable arg) {
        StringLiteralExpr n2 = (StringLiteralExpr)arg;
        if (!this.objEquals(n.getValue(), n2.getValue())) {
            return false;
        } else {
            return !this.nodeEquals(n.getComment(), n2.getComment()) ? false : true;
        }
    }

    public Boolean visit(final IntegerLiteralExpr n, final Visitable arg) {
        IntegerLiteralExpr n2 = (IntegerLiteralExpr)arg;
        if (!this.objEquals(n.getValue(), n2.getValue())) {
            return false;
        } else {
            return !this.nodeEquals(n.getComment(), n2.getComment()) ? false : true;
        }
    }

    public Boolean visit(final LongLiteralExpr n, final Visitable arg) {
        LongLiteralExpr n2 = (LongLiteralExpr)arg;
        if (!this.objEquals(n.getValue(), n2.getValue())) {
            return false;
        } else {
            return !this.nodeEquals(n.getComment(), n2.getComment()) ? false : true;
        }
    }

    public Boolean visit(final CharLiteralExpr n, final Visitable arg) {
        CharLiteralExpr n2 = (CharLiteralExpr)arg;
        if (!this.objEquals(n.getValue(), n2.getValue())) {
            return false;
        } else {
            return !this.nodeEquals(n.getComment(), n2.getComment()) ? false : true;
        }
    }

    public Boolean visit(final DoubleLiteralExpr n, final Visitable arg) {
        DoubleLiteralExpr n2 = (DoubleLiteralExpr)arg;
        if (!this.objEquals(n.getValue(), n2.getValue())) {
            return false;
        } else {
            return !this.nodeEquals(n.getComment(), n2.getComment()) ? false : true;
        }
    }

    public Boolean visit(final BooleanLiteralExpr n, final Visitable arg) {
        BooleanLiteralExpr n2 = (BooleanLiteralExpr)arg;
        if (!this.objEquals(n.isValue(), n2.isValue())) {
            return false;
        } else {
            return !this.nodeEquals(n.getComment(), n2.getComment()) ? false : true;
        }
    }

    public Boolean visit(final NullLiteralExpr n, final Visitable arg) {
        NullLiteralExpr n2 = (NullLiteralExpr)arg;
        return !this.nodeEquals(n.getComment(), n2.getComment()) ? false : true;
    }

    public Boolean visit(final MethodCallExpr n, final Visitable arg) {
        MethodCallExpr n2 = (MethodCallExpr)arg;
        if (!this.nodesEquals(n.getArguments(), n2.getArguments())) {
            return false;
        } else if (!this.nodeEquals(n.getName(), n2.getName())) {
            return false;
        } else if (!this.nodeEquals(n.getScope(), n2.getScope())) {
            return false;
        } else if (!this.nodesEquals(n.getTypeArguments(), n2.getTypeArguments())) {
            return false;
        } else {
            return !this.nodeEquals(n.getComment(), n2.getComment()) ? false : true;
        }
    }

    public Boolean visit(final NameExpr n, final Visitable arg) {
        NameExpr n2 = (NameExpr)arg;
        if (!this.nodeEquals(n.getName(), n2.getName())) {
            return false;
        } else {
            return !this.nodeEquals(n.getComment(), n2.getComment()) ? false : true;
        }
    }

    public Boolean visit(final ObjectCreationExpr n, final Visitable arg) {
        ObjectCreationExpr n2 = (ObjectCreationExpr)arg;
        if (!this.nodesEquals(n.getAnonymousClassBody(), n2.getAnonymousClassBody())) {
            return false;
        } else if (!this.nodesEquals(n.getArguments(), n2.getArguments())) {
            return false;
        } else if (!this.nodeEquals(n.getScope(), n2.getScope())) {
            return false;
        } else if (!this.nodeEquals(n.getType(), n2.getType())) {
            return false;
        } else if (!this.nodesEquals(n.getTypeArguments(), n2.getTypeArguments())) {
            return false;
        } else {
            return !this.nodeEquals(n.getComment(), n2.getComment()) ? false : true;
        }
    }

    public Boolean visit(final Name n, final Visitable arg) {
        Name n2 = (Name)arg;
        if (!this.objEquals(n.getIdentifier(), n2.getIdentifier())) {
            return false;
        } else if (!this.nodeEquals(n.getQualifier(), n2.getQualifier())) {
            return false;
        } else {
            return !this.nodeEquals(n.getComment(), n2.getComment()) ? false : true;
        }
    }

    public Boolean visit(final SimpleName n, final Visitable arg) {
        SimpleName n2 = (SimpleName)arg;
        if (!this.objEquals(n.getIdentifier(), n2.getIdentifier())) {
            return false;
        } else {
            return !this.nodeEquals(n.getComment(), n2.getComment()) ? false : true;
        }
    }

    public Boolean visit(final ThisExpr n, final Visitable arg) {
        ThisExpr n2 = (ThisExpr)arg;
        if (!this.nodeEquals(n.getTypeName(), n2.getTypeName())) {
            return false;
        } else {
            return !this.nodeEquals(n.getComment(), n2.getComment()) ? false : true;
        }
    }

    public Boolean visit(final SuperExpr n, final Visitable arg) {
        SuperExpr n2 = (SuperExpr)arg;
        if (!this.nodeEquals(n.getTypeName(), n2.getTypeName())) {
            return false;
        } else {
            return !this.nodeEquals(n.getComment(), n2.getComment()) ? false : true;
        }
    }

    public Boolean visit(final UnaryExpr n, final Visitable arg) {
        UnaryExpr n2 = (UnaryExpr)arg;
        if (!this.nodeEquals(n.getExpression(), n2.getExpression())) {
            return false;
        } else if (!this.objEquals(n.getOperator(), n2.getOperator())) {
            return false;
        } else {
            return !this.nodeEquals(n.getComment(), n2.getComment()) ? false : true;
        }
    }

    public Boolean visit(final VariableDeclarationExpr n, final Visitable arg) {
        VariableDeclarationExpr n2 = (VariableDeclarationExpr)arg;
        if (!this.nodesEquals(n.getAnnotations(), n2.getAnnotations())) {
            return false;
        } else if (!this.nodesEquals(n.getModifiers(), n2.getModifiers())) {
            return false;
        } else if (!this.nodesEquals(n.getVariables(), n2.getVariables())) {
            return false;
        } else {
            return !this.nodeEquals(n.getComment(), n2.getComment()) ? false : true;
        }
    }

    public Boolean visit(final MarkerAnnotationExpr n, final Visitable arg) {
        MarkerAnnotationExpr n2 = (MarkerAnnotationExpr)arg;
        if (!this.nodeEquals(n.getName(), n2.getName())) {
            return false;
        } else {
            return !this.nodeEquals(n.getComment(), n2.getComment()) ? false : true;
        }
    }

    public Boolean visit(final SingleMemberAnnotationExpr n, final Visitable arg) {
        SingleMemberAnnotationExpr n2 = (SingleMemberAnnotationExpr)arg;
        if (!this.nodeEquals(n.getMemberValue(), n2.getMemberValue())) {
            return false;
        } else if (!this.nodeEquals(n.getName(), n2.getName())) {
            return false;
        } else {
            return !this.nodeEquals(n.getComment(), n2.getComment()) ? false : true;
        }
    }

    public Boolean visit(final NormalAnnotationExpr n, final Visitable arg) {
        NormalAnnotationExpr n2 = (NormalAnnotationExpr)arg;
        if (!this.nodesEquals(n.getPairs(), n2.getPairs())) {
            return false;
        } else if (!this.nodeEquals(n.getName(), n2.getName())) {
            return false;
        } else {
            return !this.nodeEquals(n.getComment(), n2.getComment()) ? false : true;
        }
    }

    public Boolean visit(final MemberValuePair n, final Visitable arg) {
        MemberValuePair n2 = (MemberValuePair)arg;
        if (!this.nodeEquals(n.getName(), n2.getName())) {
            return false;
        } else if (!this.nodeEquals(n.getValue(), n2.getValue())) {
            return false;
        } else {
            return !this.nodeEquals(n.getComment(), n2.getComment()) ? false : true;
        }
    }

    public Boolean visit(final ExplicitConstructorInvocationStmt n, final Visitable arg) {
        ExplicitConstructorInvocationStmt n2 = (ExplicitConstructorInvocationStmt)arg;
        if (!this.nodesEquals(n.getArguments(), n2.getArguments())) {
            return false;
        } else if (!this.nodeEquals(n.getExpression(), n2.getExpression())) {
            return false;
        } else if (!this.objEquals(n.isThis(), n2.isThis())) {
            return false;
        } else if (!this.nodesEquals(n.getTypeArguments(), n2.getTypeArguments())) {
            return false;
        } else {
            return !this.nodeEquals(n.getComment(), n2.getComment()) ? false : true;
        }
    }

    public Boolean visit(final LocalClassDeclarationStmt n, final Visitable arg) {
        LocalClassDeclarationStmt n2 = (LocalClassDeclarationStmt)arg;
        if (!this.nodeEquals(n.getClassDeclaration(), n2.getClassDeclaration())) {
            return false;
        } else {
            return !this.nodeEquals(n.getComment(), n2.getComment()) ? false : true;
        }
    }

    public Boolean visit(final LocalRecordDeclarationStmt n, final Visitable arg) {
        LocalRecordDeclarationStmt n2 = (LocalRecordDeclarationStmt)arg;
        if (!this.nodeEquals(n.getRecordDeclaration(), n2.getRecordDeclaration())) {
            return false;
        } else {
            return !this.nodeEquals(n.getComment(), n2.getComment()) ? false : true;
        }
    }

    public Boolean visit(final AssertStmt n, final Visitable arg) {
        AssertStmt n2 = (AssertStmt)arg;
        if (!this.nodeEquals(n.getCheck(), n2.getCheck())) {
            return false;
        } else if (!this.nodeEquals(n.getMessage(), n2.getMessage())) {
            return false;
        } else {
            return !this.nodeEquals(n.getComment(), n2.getComment()) ? false : true;
        }
    }

    public Boolean visit(final BlockStmt n, final Visitable arg) {
        BlockStmt n2 = (BlockStmt)arg;
        if (!this.nodesEquals(n.getContracts(), n2.getContracts())) {
            return false;
        } else if (!this.nodesEquals(n.getStatements(), n2.getStatements())) {
            return false;
        } else {
            return !this.nodeEquals(n.getComment(), n2.getComment()) ? false : true;
        }
    }

    public Boolean visit(final LabeledStmt n, final Visitable arg) {
        LabeledStmt n2 = (LabeledStmt)arg;
        if (!this.nodeEquals(n.getLabel(), n2.getLabel())) {
            return false;
        } else if (!this.nodeEquals(n.getStatement(), n2.getStatement())) {
            return false;
        } else {
            return !this.nodeEquals(n.getComment(), n2.getComment()) ? false : true;
        }
    }

    public Boolean visit(final EmptyStmt n, final Visitable arg) {
        EmptyStmt n2 = (EmptyStmt)arg;
        return !this.nodeEquals(n.getComment(), n2.getComment()) ? false : true;
    }

    public Boolean visit(final ExpressionStmt n, final Visitable arg) {
        ExpressionStmt n2 = (ExpressionStmt)arg;
        if (!this.nodeEquals(n.getExpression(), n2.getExpression())) {
            return false;
        } else {
            return !this.nodeEquals(n.getComment(), n2.getComment()) ? false : true;
        }
    }

    public Boolean visit(final SwitchStmt n, final Visitable arg) {
        SwitchStmt n2 = (SwitchStmt)arg;
        if (!this.nodesEquals(n.getEntries(), n2.getEntries())) {
            return false;
        } else if (!this.nodeEquals(n.getSelector(), n2.getSelector())) {
            return false;
        } else {
            return !this.nodeEquals(n.getComment(), n2.getComment()) ? false : true;
        }
    }

    public Boolean visit(final SwitchEntry n, final Visitable arg) {
        SwitchEntry n2 = (SwitchEntry)arg;
        if (!this.nodesEquals(n.getLabels(), n2.getLabels())) {
            return false;
        } else if (!this.nodesEquals(n.getStatements(), n2.getStatements())) {
            return false;
        } else if (!this.objEquals(n.getType(), n2.getType())) {
            return false;
        } else {
            return !this.nodeEquals(n.getComment(), n2.getComment()) ? false : true;
        }
    }

    public Boolean visit(final BreakStmt n, final Visitable arg) {
        BreakStmt n2 = (BreakStmt)arg;
        if (!this.nodeEquals(n.getLabel(), n2.getLabel())) {
            return false;
        } else {
            return !this.nodeEquals(n.getComment(), n2.getComment()) ? false : true;
        }
    }

    public Boolean visit(final ReturnStmt n, final Visitable arg) {
        ReturnStmt n2 = (ReturnStmt)arg;
        if (!this.nodeEquals(n.getExpression(), n2.getExpression())) {
            return false;
        } else {
            return !this.nodeEquals(n.getComment(), n2.getComment()) ? false : true;
        }
    }

    public Boolean visit(final IfStmt n, final Visitable arg) {
        IfStmt n2 = (IfStmt)arg;
        if (!this.nodeEquals(n.getCondition(), n2.getCondition())) {
            return false;
        } else if (!this.nodeEquals(n.getElseStmt(), n2.getElseStmt())) {
            return false;
        } else if (!this.nodeEquals(n.getThenStmt(), n2.getThenStmt())) {
            return false;
        } else {
            return !this.nodeEquals(n.getComment(), n2.getComment()) ? false : true;
        }
    }

    public Boolean visit(final WhileStmt n, final Visitable arg) {
        WhileStmt n2 = (WhileStmt)arg;
        if (!this.nodeEquals(n.getBody(), n2.getBody())) {
            return false;
        } else if (!this.nodeEquals(n.getCondition(), n2.getCondition())) {
            return false;
        } else if (!this.nodesEquals(n.getContracts(), n2.getContracts())) {
            return false;
        } else {
            return !this.nodeEquals(n.getComment(), n2.getComment()) ? false : true;
        }
    }

    public Boolean visit(final ContinueStmt n, final Visitable arg) {
        ContinueStmt n2 = (ContinueStmt)arg;
        if (!this.nodeEquals(n.getLabel(), n2.getLabel())) {
            return false;
        } else {
            return !this.nodeEquals(n.getComment(), n2.getComment()) ? false : true;
        }
    }

    public Boolean visit(final DoStmt n, final Visitable arg) {
        DoStmt n2 = (DoStmt)arg;
        if (!this.nodeEquals(n.getBody(), n2.getBody())) {
            return false;
        } else if (!this.nodeEquals(n.getCondition(), n2.getCondition())) {
            return false;
        } else if (!this.nodesEquals(n.getContracts(), n2.getContracts())) {
            return false;
        } else {
            return !this.nodeEquals(n.getComment(), n2.getComment()) ? false : true;
        }
    }

    public Boolean visit(final ForEachStmt n, final Visitable arg) {
        ForEachStmt n2 = (ForEachStmt)arg;
        if (!this.nodeEquals(n.getBody(), n2.getBody())) {
            return false;
        } else if (!this.nodesEquals(n.getContracts(), n2.getContracts())) {
            return false;
        } else if (!this.nodeEquals(n.getIterable(), n2.getIterable())) {
            return false;
        } else if (!this.nodeEquals(n.getVariable(), n2.getVariable())) {
            return false;
        } else {
            return !this.nodeEquals(n.getComment(), n2.getComment()) ? false : true;
        }
    }

    public Boolean visit(final ForStmt n, final Visitable arg) {
        ForStmt n2 = (ForStmt)arg;
        if (!this.nodeEquals(n.getBody(), n2.getBody())) {
            return false;
        } else if (!this.nodeEquals(n.getCompare(), n2.getCompare())) {
            return false;
        } else if (!this.nodesEquals(n.getContracts(), n2.getContracts())) {
            return false;
        } else if (!this.nodesEquals(n.getInitialization(), n2.getInitialization())) {
            return false;
        } else if (!this.nodesEquals(n.getUpdate(), n2.getUpdate())) {
            return false;
        } else {
            return !this.nodeEquals(n.getComment(), n2.getComment()) ? false : true;
        }
    }

    public Boolean visit(final ThrowStmt n, final Visitable arg) {
        ThrowStmt n2 = (ThrowStmt)arg;
        if (!this.nodeEquals(n.getExpression(), n2.getExpression())) {
            return false;
        } else {
            return !this.nodeEquals(n.getComment(), n2.getComment()) ? false : true;
        }
    }

    public Boolean visit(final SynchronizedStmt n, final Visitable arg) {
        SynchronizedStmt n2 = (SynchronizedStmt)arg;
        if (!this.nodeEquals(n.getBody(), n2.getBody())) {
            return false;
        } else if (!this.nodeEquals(n.getExpression(), n2.getExpression())) {
            return false;
        } else {
            return !this.nodeEquals(n.getComment(), n2.getComment()) ? false : true;
        }
    }

    public Boolean visit(final TryStmt n, final Visitable arg) {
        TryStmt n2 = (TryStmt)arg;
        if (!this.nodesEquals(n.getCatchClauses(), n2.getCatchClauses())) {
            return false;
        } else if (!this.nodeEquals(n.getFinallyBlock(), n2.getFinallyBlock())) {
            return false;
        } else if (!this.nodesEquals(n.getResources(), n2.getResources())) {
            return false;
        } else if (!this.nodeEquals(n.getTryBlock(), n2.getTryBlock())) {
            return false;
        } else {
            return !this.nodeEquals(n.getComment(), n2.getComment()) ? false : true;
        }
    }

    public Boolean visit(final CatchClause n, final Visitable arg) {
        CatchClause n2 = (CatchClause)arg;
        if (!this.nodeEquals(n.getBody(), n2.getBody())) {
            return false;
        } else if (!this.nodeEquals(n.getParameter(), n2.getParameter())) {
            return false;
        } else {
            return !this.nodeEquals(n.getComment(), n2.getComment()) ? false : true;
        }
    }

    public Boolean visit(final LambdaExpr n, final Visitable arg) {
        LambdaExpr n2 = (LambdaExpr)arg;
        if (!this.nodeEquals(n.getBody(), n2.getBody())) {
            return false;
        } else if (!this.nodesEquals(n.getContracts(), n2.getContracts())) {
            return false;
        } else if (!this.objEquals(n.isEnclosingParameters(), n2.isEnclosingParameters())) {
            return false;
        } else if (!this.nodesEquals(n.getParameters(), n2.getParameters())) {
            return false;
        } else {
            return !this.nodeEquals(n.getComment(), n2.getComment()) ? false : true;
        }
    }

    public Boolean visit(final MethodReferenceExpr n, final Visitable arg) {
        MethodReferenceExpr n2 = (MethodReferenceExpr)arg;
        if (!this.objEquals(n.getIdentifier(), n2.getIdentifier())) {
            return false;
        } else if (!this.nodeEquals(n.getScope(), n2.getScope())) {
            return false;
        } else if (!this.nodesEquals(n.getTypeArguments(), n2.getTypeArguments())) {
            return false;
        } else {
            return !this.nodeEquals(n.getComment(), n2.getComment()) ? false : true;
        }
    }

    public Boolean visit(final TypeExpr n, final Visitable arg) {
        TypeExpr n2 = (TypeExpr)arg;
        if (!this.nodeEquals(n.getType(), n2.getType())) {
            return false;
        } else {
            return !this.nodeEquals(n.getComment(), n2.getComment()) ? false : true;
        }
    }

    public Boolean visit(final ImportDeclaration n, final Visitable arg) {
        ImportDeclaration n2 = (ImportDeclaration)arg;
        if (!this.objEquals(n.isAsterisk(), n2.isAsterisk())) {
            return false;
        } else if (!this.objEquals(n.isJmlModel(), n2.isJmlModel())) {
            return false;
        } else if (!this.objEquals(n.isStatic(), n2.isStatic())) {
            return false;
        } else if (!this.nodeEquals(n.getName(), n2.getName())) {
            return false;
        } else {
            return !this.nodeEquals(n.getComment(), n2.getComment()) ? false : true;
        }
    }

    public Boolean visit(NodeList n, Visitable arg) {
        return this.nodesEquals(n, (NodeList)arg);
    }

    public Boolean visit(final ModuleDeclaration n, final Visitable arg) {
        ModuleDeclaration n2 = (ModuleDeclaration)arg;
        if (!this.nodesEquals(n.getAnnotations(), n2.getAnnotations())) {
            return false;
        } else if (!this.nodesEquals(n.getDirectives(), n2.getDirectives())) {
            return false;
        } else if (!this.objEquals(n.isOpen(), n2.isOpen())) {
            return false;
        } else if (!this.nodeEquals(n.getName(), n2.getName())) {
            return false;
        } else {
            return !this.nodeEquals(n.getComment(), n2.getComment()) ? false : true;
        }
    }

    public Boolean visit(final ModuleRequiresDirective n, final Visitable arg) {
        ModuleRequiresDirective n2 = (ModuleRequiresDirective)arg;
        if (!this.nodesEquals(n.getModifiers(), n2.getModifiers())) {
            return false;
        } else if (!this.nodeEquals(n.getName(), n2.getName())) {
            return false;
        } else {
            return !this.nodeEquals(n.getComment(), n2.getComment()) ? false : true;
        }
    }

    public Boolean visit(final ModuleExportsDirective n, final Visitable arg) {
        ModuleExportsDirective n2 = (ModuleExportsDirective)arg;
        if (!this.nodesEquals(n.getModuleNames(), n2.getModuleNames())) {
            return false;
        } else if (!this.nodeEquals(n.getName(), n2.getName())) {
            return false;
        } else {
            return !this.nodeEquals(n.getComment(), n2.getComment()) ? false : true;
        }
    }

    public Boolean visit(final ModuleProvidesDirective n, final Visitable arg) {
        ModuleProvidesDirective n2 = (ModuleProvidesDirective)arg;
        if (!this.nodeEquals(n.getName(), n2.getName())) {
            return false;
        } else if (!this.nodesEquals(n.getWith(), n2.getWith())) {
            return false;
        } else {
            return !this.nodeEquals(n.getComment(), n2.getComment()) ? false : true;
        }
    }

    public Boolean visit(final ModuleUsesDirective n, final Visitable arg) {
        ModuleUsesDirective n2 = (ModuleUsesDirective)arg;
        if (!this.nodeEquals(n.getName(), n2.getName())) {
            return false;
        } else {
            return !this.nodeEquals(n.getComment(), n2.getComment()) ? false : true;
        }
    }

    public Boolean visit(final ModuleOpensDirective n, final Visitable arg) {
        ModuleOpensDirective n2 = (ModuleOpensDirective)arg;
        if (!this.nodesEquals(n.getModuleNames(), n2.getModuleNames())) {
            return false;
        } else if (!this.nodeEquals(n.getName(), n2.getName())) {
            return false;
        } else {
            return !this.nodeEquals(n.getComment(), n2.getComment()) ? false : true;
        }
    }

    public Boolean visit(final UnparsableStmt n, final Visitable arg) {
        UnparsableStmt n2 = (UnparsableStmt)arg;
        return !this.nodeEquals(n.getComment(), n2.getComment()) ? false : true;
    }

    public Boolean visit(final ReceiverParameter n, final Visitable arg) {
        ReceiverParameter n2 = (ReceiverParameter)arg;
        if (!this.nodesEquals(n.getAnnotations(), n2.getAnnotations())) {
            return false;
        } else if (!this.nodeEquals(n.getName(), n2.getName())) {
            return false;
        } else if (!this.nodeEquals(n.getType(), n2.getType())) {
            return false;
        } else {
            return !this.nodeEquals(n.getComment(), n2.getComment()) ? false : true;
        }
    }

    public Boolean visit(final VarType n, final Visitable arg) {
        VarType n2 = (VarType)arg;
        if (!this.nodesEquals(n.getAnnotations(), n2.getAnnotations())) {
            return false;
        } else {
            return !this.nodeEquals(n.getComment(), n2.getComment()) ? false : true;
        }
    }

    public Boolean visit(final Modifier n, final Visitable arg) {
        Modifier n2 = (Modifier)arg;
        if (!this.objEquals(n.getKeyword(), n2.getKeyword())) {
            return false;
        } else {
            return !this.nodeEquals(n.getComment(), n2.getComment()) ? false : true;
        }
    }

    public Boolean visit(final SwitchExpr n, final Visitable arg) {
        SwitchExpr n2 = (SwitchExpr)arg;
        if (!this.nodesEquals(n.getEntries(), n2.getEntries())) {
            return false;
        } else if (!this.nodeEquals(n.getSelector(), n2.getSelector())) {
            return false;
        } else {
            return !this.nodeEquals(n.getComment(), n2.getComment()) ? false : true;
        }
    }

    public Boolean visit(final YieldStmt n, final Visitable arg) {
        YieldStmt n2 = (YieldStmt)arg;
        if (!this.nodeEquals(n.getExpression(), n2.getExpression())) {
            return false;
        } else {
            return !this.nodeEquals(n.getComment(), n2.getComment()) ? false : true;
        }
    }

    public Boolean visit(final TextBlockLiteralExpr n, final Visitable arg) {
        TextBlockLiteralExpr n2 = (TextBlockLiteralExpr)arg;
        if (!this.objEquals(n.getValue(), n2.getValue())) {
            return false;
        } else {
            return !this.nodeEquals(n.getComment(), n2.getComment()) ? false : true;
        }
    }

    public Boolean visit(final PatternExpr n, final Visitable arg) {
        PatternExpr n2 = (PatternExpr)arg;
        if (!this.nodesEquals(n.getModifiers(), n2.getModifiers())) {
            return false;
        } else if (!this.nodeEquals(n.getName(), n2.getName())) {
            return false;
        } else if (!this.nodeEquals(n.getType(), n2.getType())) {
            return false;
        } else {
            return !this.nodeEquals(n.getComment(), n2.getComment()) ? false : true;
        }
    }

    public Boolean visit(final JmlClauseLabel n, final Visitable arg) {
        JmlClauseLabel n2 = (JmlClauseLabel)arg;
        if (!this.nodeEquals(n.getExpr(), n2.getExpr())) {
            return false;
        } else if (!this.objEquals(n.getKind(), n2.getKind())) {
            return false;
        } else if (!this.nodeEquals(n.getLabel(), n2.getLabel())) {
            return false;
        } else if (!this.nodeEquals(n.getName(), n2.getName())) {
            return false;
        } else {
            return !this.nodeEquals(n.getComment(), n2.getComment()) ? false : true;
        }
    }

    public Boolean visit(final JmlExpressionStmt n, final Visitable arg) {
        JmlExpressionStmt n2 = (JmlExpressionStmt)arg;
        if (!this.nodeEquals(n.getExpression(), n2.getExpression())) {
            return false;
        } else if (!this.nodesEquals(n.getJmlTags(), n2.getJmlTags())) {
            return false;
        } else if (!this.objEquals(n.getKind(), n2.getKind())) {
            return false;
        } else {
            return !this.nodeEquals(n.getComment(), n2.getComment()) ? false : true;
        }
    }

    public Boolean visit(final JmlQuantifiedExpr n, final Visitable arg) {
        JmlQuantifiedExpr n2 = (JmlQuantifiedExpr)arg;
        if (!this.objEquals(n.getBinder(), n2.getBinder())) {
            return false;
        } else if (!this.nodesEquals(n.getExpressions(), n2.getExpressions())) {
            return false;
        } else if (!this.nodesEquals(n.getVariables(), n2.getVariables())) {
            return false;
        } else {
            return !this.nodeEquals(n.getComment(), n2.getComment()) ? false : true;
        }
    }

    public Boolean visit(final JmlLabelExpr n, final Visitable arg) {
        JmlLabelExpr n2 = (JmlLabelExpr)arg;
        if (!this.nodeEquals(n.getExpression(), n2.getExpression())) {
            return false;
        } else if (!this.objEquals(n.getKind(), n2.getKind())) {
            return false;
        } else if (!this.nodeEquals(n.getLabel(), n2.getLabel())) {
            return false;
        } else {
            return !this.nodeEquals(n.getComment(), n2.getComment()) ? false : true;
        }
    }

    public Boolean visit(final JmlLetExpr n, final Visitable arg) {
        JmlLetExpr n2 = (JmlLetExpr)arg;
        if (!this.nodeEquals(n.getBody(), n2.getBody())) {
            return false;
        } else if (!this.nodeEquals(n.getVariables(), n2.getVariables())) {
            return false;
        } else {
            return !this.nodeEquals(n.getComment(), n2.getComment()) ? false : true;
        }
    }

    public Boolean visit(final JmlMultiCompareExpr n, final Visitable arg) {
        JmlMultiCompareExpr n2 = (JmlMultiCompareExpr)arg;
        if (!this.nodesEquals(n.getExpressions(), n2.getExpressions())) {
            return false;
        } else if (!this.objEquals(n.getOperators(), n2.getOperators())) {
            return false;
        } else {
            return !this.nodeEquals(n.getComment(), n2.getComment()) ? false : true;
        }
    }

    public Boolean visit(final JmlSimpleExprClause n, final Visitable arg) {
        JmlSimpleExprClause n2 = (JmlSimpleExprClause)arg;
        if (!this.nodeEquals(n.getExpression(), n2.getExpression())) {
            return false;
        } else if (!this.nodesEquals(n.getHeaps(), n2.getHeaps())) {
            return false;
        } else if (!this.objEquals(n.getKind(), n2.getKind())) {
            return false;
        } else if (!this.nodeEquals(n.getName(), n2.getName())) {
            return false;
        } else {
            return !this.nodeEquals(n.getComment(), n2.getComment()) ? false : true;
        }
    }

    public Boolean visit(final JmlSignalsClause n, final Visitable arg) {
        JmlSignalsClause n2 = (JmlSignalsClause)arg;
        if (!this.nodeEquals(n.getExpression(), n2.getExpression())) {
            return false;
        } else if (!this.nodeEquals(n.getParameter(), n2.getParameter())) {
            return false;
        } else if (!this.nodeEquals(n.getName(), n2.getName())) {
            return false;
        } else {
            return !this.nodeEquals(n.getComment(), n2.getComment()) ? false : true;
        }
    }

    public Boolean visit(final JmlSignalsOnlyClause n, final Visitable arg) {
        JmlSignalsOnlyClause n2 = (JmlSignalsOnlyClause)arg;
        if (!this.nodesEquals(n.getTypes(), n2.getTypes())) {
            return false;
        } else if (!this.nodeEquals(n.getName(), n2.getName())) {
            return false;
        } else {
            return !this.nodeEquals(n.getComment(), n2.getComment()) ? false : true;
        }
    }

    public Boolean visit(final JmlUnreachableStmt n, final Visitable arg) {
        JmlUnreachableStmt n2 = (JmlUnreachableStmt)arg;
        if (!this.nodesEquals(n.getJmlTags(), n2.getJmlTags())) {
            return false;
        } else {
            return !this.nodeEquals(n.getComment(), n2.getComment()) ? false : true;
        }
    }

    public Boolean visit(final JmlCallableClause n, final Visitable arg) {
        JmlCallableClause n2 = (JmlCallableClause)arg;
        if (!this.nodesEquals(n.getMethodSignatures(), n2.getMethodSignatures())) {
            return false;
        } else if (!this.nodeEquals(n.getName(), n2.getName())) {
            return false;
        } else {
            return !this.nodeEquals(n.getComment(), n2.getComment()) ? false : true;
        }
    }

    public Boolean visit(final JmlForallClause n, final Visitable arg) {
        JmlForallClause n2 = (JmlForallClause)arg;
        if (!this.nodesEquals(n.getBoundedVariables(), n2.getBoundedVariables())) {
            return false;
        } else if (!this.nodeEquals(n.getName(), n2.getName())) {
            return false;
        } else {
            return !this.nodeEquals(n.getComment(), n2.getComment()) ? false : true;
        }
    }

    public Boolean visit(final JmlRefiningStmt n, final Visitable arg) {
        JmlRefiningStmt n2 = (JmlRefiningStmt)arg;
        if (!this.nodesEquals(n.getJmlTags(), n2.getJmlTags())) {
            return false;
        } else {
            return !this.nodeEquals(n.getComment(), n2.getComment()) ? false : true;
        }
    }

    public Boolean visit(final JmlClauseIf n, final Visitable arg) {
        JmlClauseIf n2 = (JmlClauseIf)arg;
        if (!this.nodeEquals(n.getCondition(), n2.getCondition())) {
            return false;
        } else if (!this.objEquals(n.getKind(), n2.getKind())) {
            return false;
        } else if (!this.nodeEquals(n.getThen(), n2.getThen())) {
            return false;
        } else if (!this.nodeEquals(n.getName(), n2.getName())) {
            return false;
        } else {
            return !this.nodeEquals(n.getComment(), n2.getComment()) ? false : true;
        }
    }

    public Boolean visit(final JmlClassExprDeclaration n, final Visitable arg) {
        JmlClassExprDeclaration n2 = (JmlClassExprDeclaration)arg;
        if (!this.nodeEquals(n.getInvariant(), n2.getInvariant())) {
            return false;
        } else if (!this.nodesEquals(n.getJmlTags(), n2.getJmlTags())) {
            return false;
        } else if (!this.nodeEquals(n.getKind(), n2.getKind())) {
            return false;
        } else if (!this.nodesEquals(n.getModifiers(), n2.getModifiers())) {
            return false;
        } else if (!this.nodeEquals(n.getName(), n2.getName())) {
            return false;
        } else if (!this.nodesEquals(n.getAnnotations(), n2.getAnnotations())) {
            return false;
        } else {
            return !this.nodeEquals(n.getComment(), n2.getComment()) ? false : true;
        }
    }

    public Boolean visit(final JmlClassAccessibleDeclaration n, final Visitable arg) {
        JmlClassAccessibleDeclaration n2 = (JmlClassAccessibleDeclaration)arg;
        if (!this.nodesEquals(n.getExpressions(), n2.getExpressions())) {
            return false;
        } else if (!this.nodesEquals(n.getJmlTags(), n2.getJmlTags())) {
            return false;
        } else if (!this.nodeEquals(n.getMeasuredBy(), n2.getMeasuredBy())) {
            return false;
        } else if (!this.nodesEquals(n.getModifiers(), n2.getModifiers())) {
            return false;
        } else if (!this.nodeEquals(n.getVariable(), n2.getVariable())) {
            return false;
        } else if (!this.nodesEquals(n.getAnnotations(), n2.getAnnotations())) {
            return false;
        } else {
            return !this.nodeEquals(n.getComment(), n2.getComment()) ? false : true;
        }
    }

    public Boolean visit(final JmlRepresentsDeclaration n, final Visitable arg) {
        JmlRepresentsDeclaration n2 = (JmlRepresentsDeclaration)arg;
        if (!this.nodeEquals(n.getExpr(), n2.getExpr())) {
            return false;
        } else if (!this.nodesEquals(n.getJmlTags(), n2.getJmlTags())) {
            return false;
        } else if (!this.nodesEquals(n.getModifiers(), n2.getModifiers())) {
            return false;
        } else if (!this.nodeEquals(n.getName(), n2.getName())) {
            return false;
        } else if (!this.nodesEquals(n.getAnnotations(), n2.getAnnotations())) {
            return false;
        } else {
            return !this.nodeEquals(n.getComment(), n2.getComment()) ? false : true;
        }
    }

    public Boolean visit(final JmlContract n, final Visitable arg) {
        JmlContract n2 = (JmlContract)arg;
        if (!this.objEquals(n.getBehavior(), n2.getBehavior())) {
            return false;
        } else if (!this.nodesEquals(n.getClauses(), n2.getClauses())) {
            return false;
        } else if (!this.nodesEquals(n.getJmlTags(), n2.getJmlTags())) {
            return false;
        } else if (!this.nodesEquals(n.getModifiers(), n2.getModifiers())) {
            return false;
        } else if (!this.nodeEquals(n.getName(), n2.getName())) {
            return false;
        } else if (!this.nodesEquals(n.getSubContracts(), n2.getSubContracts())) {
            return false;
        } else if (!this.objEquals(n.getType(), n2.getType())) {
            return false;
        } else {
            return !this.nodeEquals(n.getComment(), n2.getComment()) ? false : true;
        }
    }

    public Boolean visit(final JmlSetComprehensionExpr n, final Visitable arg) {
        JmlSetComprehensionExpr n2 = (JmlSetComprehensionExpr)arg;
        if (!this.nodeEquals(n.getBinding(), n2.getBinding())) {
            return false;
        } else if (!this.nodeEquals(n.getPredicate(), n2.getPredicate())) {
            return false;
        } else {
            return !this.nodeEquals(n.getComment(), n2.getComment()) ? false : true;
        }
    }

    public Boolean visit(final JmlGhostStmt n, final Visitable arg) {
        JmlGhostStmt n2 = (JmlGhostStmt)arg;
        if (!this.nodesEquals(n.getJmlTags(), n2.getJmlTags())) {
            return false;
        } else if (!this.nodeEquals(n.getStatement(), n2.getStatement())) {
            return false;
        } else {
            return !this.nodeEquals(n.getComment(), n2.getComment()) ? false : true;
        }
    }

    public Boolean visit(final RecordDeclaration n, final Visitable arg) {
        RecordDeclaration n2 = (RecordDeclaration)arg;
        if (!this.nodesEquals(n.getImplementedTypes(), n2.getImplementedTypes())) {
            return false;
        } else if (!this.nodesEquals(n.getParameters(), n2.getParameters())) {
            return false;
        } else if (!this.nodeEquals(n.getReceiverParameter(), n2.getReceiverParameter())) {
            return false;
        } else if (!this.nodesEquals(n.getTypeParameters(), n2.getTypeParameters())) {
            return false;
        } else if (!this.nodesEquals(n.getMembers(), n2.getMembers())) {
            return false;
        } else if (!this.nodesEquals(n.getModifiers(), n2.getModifiers())) {
            return false;
        } else if (!this.nodeEquals(n.getName(), n2.getName())) {
            return false;
        } else if (!this.nodesEquals(n.getAnnotations(), n2.getAnnotations())) {
            return false;
        } else {
            return !this.nodeEquals(n.getComment(), n2.getComment()) ? false : true;
        }
    }

    public Boolean visit(final CompactConstructorDeclaration n, final Visitable arg) {
        CompactConstructorDeclaration n2 = (CompactConstructorDeclaration)arg;
        if (!this.nodeEquals(n.getBody(), n2.getBody())) {
            return false;
        } else if (!this.nodesEquals(n.getModifiers(), n2.getModifiers())) {
            return false;
        } else if (!this.nodeEquals(n.getName(), n2.getName())) {
            return false;
        } else if (!this.nodesEquals(n.getThrownExceptions(), n2.getThrownExceptions())) {
            return false;
        } else if (!this.nodesEquals(n.getTypeParameters(), n2.getTypeParameters())) {
            return false;
        } else if (!this.nodesEquals(n.getAnnotations(), n2.getAnnotations())) {
            return false;
        } else {
            return !this.nodeEquals(n.getComment(), n2.getComment()) ? false : true;
        }
    }

    public Boolean visit(final JmlMethodDeclaration n, final Visitable arg) {
        JmlMethodDeclaration n2 = (JmlMethodDeclaration)arg;
        if (!this.nodeEquals(n.getContract(), n2.getContract())) {
            return false;
        } else if (!this.nodesEquals(n.getJmlTags(), n2.getJmlTags())) {
            return false;
        } else if (!this.nodeEquals(n.getMethodDeclaration(), n2.getMethodDeclaration())) {
            return false;
        } else if (!this.nodesEquals(n.getAnnotations(), n2.getAnnotations())) {
            return false;
        } else {
            return !this.nodeEquals(n.getComment(), n2.getComment()) ? false : true;
        }
    }

    public Boolean visit(final JmlBinaryInfixExpr n, final Visitable arg) {
        JmlBinaryInfixExpr n2 = (JmlBinaryInfixExpr)arg;
        if (!this.nodeEquals(n.getLeft(), n2.getLeft())) {
            return false;
        } else if (!this.nodeEquals(n.getOperator(), n2.getOperator())) {
            return false;
        } else if (!this.nodeEquals(n.getRight(), n2.getRight())) {
            return false;
        } else {
            return !this.nodeEquals(n.getComment(), n2.getComment()) ? false : true;
        }
    }

    public Boolean visit(final JmlDocDeclaration n, final Visitable arg) {
        JmlDocDeclaration n2 = (JmlDocDeclaration)arg;
        if (!this.nodesEquals(n.getJmlComments(), n2.getJmlComments())) {
            return false;
        } else if (!this.nodesEquals(n.getAnnotations(), n2.getAnnotations())) {
            return false;
        } else {
            return !this.nodeEquals(n.getComment(), n2.getComment()) ? false : true;
        }
    }

    public Boolean visit(final JmlDocStmt n, final Visitable arg) {
        JmlDocStmt n2 = (JmlDocStmt)arg;
        if (!this.nodesEquals(n.getJmlComments(), n2.getJmlComments())) {
            return false;
        } else {
            return !this.nodeEquals(n.getComment(), n2.getComment()) ? false : true;
        }
    }

    public Boolean visit(final JmlDoc n, final Visitable arg) {
        JmlDoc n2 = (JmlDoc)arg;
        if (!this.objEquals(n.getContent(), n2.getContent())) {
            return false;
        } else {
            return !this.nodeEquals(n.getComment(), n2.getComment()) ? false : true;
        }
    }

    public Boolean visit(final JmlDocType n, final Visitable arg) {
        JmlDocType n2 = (JmlDocType)arg;
        if (!this.nodesEquals(n.getJmlComments(), n2.getJmlComments())) {
            return false;
        } else if (!this.nodesEquals(n.getMembers(), n2.getMembers())) {
            return false;
        } else if (!this.nodesEquals(n.getModifiers(), n2.getModifiers())) {
            return false;
        } else if (!this.nodeEquals(n.getName(), n2.getName())) {
            return false;
        } else if (!this.nodesEquals(n.getAnnotations(), n2.getAnnotations())) {
            return false;
        } else {
            return !this.nodeEquals(n.getComment(), n2.getComment()) ? false : true;
        }
    }

    public Boolean visit(final JmlFieldDeclaration n, final Visitable arg) {
        JmlFieldDeclaration n2 = (JmlFieldDeclaration)arg;
        if (!this.nodeEquals(n.getDecl(), n2.getDecl())) {
            return false;
        } else if (!this.nodesEquals(n.getJmlTags(), n2.getJmlTags())) {
            return false;
        } else if (!this.nodesEquals(n.getAnnotations(), n2.getAnnotations())) {
            return false;
        } else {
            return !this.nodeEquals(n.getComment(), n2.getComment()) ? false : true;
        }
    }

    public Boolean visit(final JmlOldClause n, final Visitable arg) {
        JmlOldClause n2 = (JmlOldClause)arg;
        if (!this.nodeEquals(n.getDeclarations(), n2.getDeclarations())) {
            return false;
        } else if (!this.nodeEquals(n.getName(), n2.getName())) {
            return false;
        } else {
            return !this.nodeEquals(n.getComment(), n2.getComment()) ? false : true;
        }
    }

    public Boolean visit(final JmlTypeExpr n, final Visitable arg) {
        JmlTypeExpr n2 = (JmlTypeExpr)arg;
        if (!this.nodeEquals(n.getType(), n2.getType())) {
            return false;
        } else {
            return !this.nodeEquals(n.getComment(), n2.getComment()) ? false : true;
        }
    }

    public Boolean visit(final JmlMultiExprClause n, final Visitable arg) {
        JmlMultiExprClause n2 = (JmlMultiExprClause)arg;
        if (!this.nodesEquals(n.getExpressions(), n2.getExpressions())) {
            return false;
        } else if (!this.nodesEquals(n.getHeaps(), n2.getHeaps())) {
            return false;
        } else if (!this.objEquals(n.getKind(), n2.getKind())) {
            return false;
        } else if (!this.nodeEquals(n.getName(), n2.getName())) {
            return false;
        } else {
            return !this.nodeEquals(n.getComment(), n2.getComment()) ? false : true;
        }
    }

    public Boolean visit(final JmlBeginStmt n, final Visitable arg) {
        JmlBeginStmt n2 = (JmlBeginStmt)arg;
        if (!this.nodesEquals(n.getJmlTags(), n2.getJmlTags())) {
            return false;
        } else {
            return this.nodeEquals(n.getComment(), n2.getComment());
        }
    }

    public Boolean visit(final JmlEndStmt n, final Visitable arg) {
        JmlEndStmt n2 = (JmlEndStmt)arg;
        if (!this.nodesEquals(n.getJmlTags(), n2.getJmlTags())) {
            return false;
        } else {
            return this.nodeEquals(n.getComment(), n2.getComment());
        }
    }

    public Boolean visit(final JmlLabelStmt n, final Visitable arg) {
        JmlLabelStmt n2 = (JmlLabelStmt)arg;
        if (!this.nodesEquals(n.getJmlTags(), n2.getJmlTags())) {
            return false;
        } else if (!this.nodeEquals(n.getLabel(), n2.getLabel())) {
            return false;
        } else {
            return this.nodeEquals(n.getComment(), n2.getComment());
        }
    }

    public Boolean visit(final JmlMethodSignature n, final Visitable arg) {
        JmlMethodSignature n2 = (JmlMethodSignature)arg;
        if (!this.nodesEquals(n.getArgumentTypes(), n2.getArgumentTypes())) {
            return false;
        } else if (!this.nodeEquals(n.getName(), n2.getName())) {
            return false;
        } else if (!this.nodeEquals(n.getReceiver(), n2.getReceiver())) {
            return false;
        } else {
            return !this.nodeEquals(n.getComment(), n2.getComment()) ? false : true;
        }
    }
}
