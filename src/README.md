#LMips

```
    val L = 108

    val M = if (true) 109 else 0
    val i = for(0, cnt -> cnt != 105, count -> {
        count + 1
    }) 
    val list = [0, 1]
    each(list, println)
    
    println(L)
    println(M)
    println(i)

```

syntax:

    <decl> ::= "val" <ident> "=" <expr>
    
    <expr> ::= <term> { ("+" | "-") <term> }
    
    <term> ::= <fact> { ("*" | "/" ) }
    
    <fact> ::= <ident> | <number> | "(" <expr> ")"
    
    <number> ::= "0" | "1" | "2" | ... "9"
