/*
 * Sonar C++ Plugin (Community)
 * Copyright (C) 2011 Waleri Enns and CONTACT Software GmbH
 * dev@sonar.codehaus.org
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02
 */
package org.sonar.cxx.parser;

import com.sonar.sslr.api.Grammar;
import com.sonar.sslr.impl.Parser;
import com.sonar.sslr.squid.SquidAstVisitorContext;
import org.junit.Test;

import static org.sonar.sslr.tests.Assertions.assertThat;
import static org.mockito.Mockito.mock;

public class AttributedAtlTest extends ParserBaseTest {

  @Test
  public void vcAtlDeclaration() {
    p.setRootRule(g.rule(CxxGrammarImpl.declaration));

    assertThat(p).matches("[x];");
  }

  @Test
  public void vcAtlEnum() {
    p.setRootRule(g.rule(CxxGrammarImpl.enumSpecifier));

    assertThat(p).matches("[x] enum X {}");
  }

  @Test
  public void vcAtlClass() {
    p.setRootRule(g.rule(CxxGrammarImpl.classSpecifier));

    assertThat(p).matches("[x] class X {}");
    assertThat(p).matches("[x] struct X {}");
  }

  @Test
  public void vcAtlMember() {
    p.setRootRule(g.rule(CxxGrammarImpl.memberSpecification));

    assertThat(p).matches("[x] int m([y] int p);");
  }

  @Test
  public void vcAtlRealWorldExample() {
    p.setRootRule(g.rule(CxxGrammarImpl.translationUnit));

    assertThat(p).matches(
      "  [module(name=\"MyModule\")];"
      + "[emitidl(false)];"
      + "[export, helpstring(\"description\")] enum MyEnum {};"
      + "["
      + "  dispinterface,"
      + "  nonextensible,"
      + "  hidden,"
      + "  uuid(\"0815\"),"
      + "  helpstring(\"description\")"
      + "]"
      + "struct IMyInterface"
      + "{"
      + "  [id(1), helpstring(\"description\")] HRESULT M1(int p1);"
      + "  [propget, id(DISPID_VALUE), helpstring(\"description\")] HRESULT M2([in] VARIANT p1, [out, retval] MyService** p2);"
      + "};"
    );
  }
}
