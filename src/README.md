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

# 出力後のファイルの実行方法

https://ja.wikipedia.org/wiki/SPIM

1. SPIMをインストールする
2. ターミナル上で`spim load res.as`を入力する

https://github.com/ShebangDog/LMips/assets/38370581/1df0f077-903d-40e9-a912-c8301afc9e69


