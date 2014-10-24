#General
#[condition][]There is an? {aType} {aVar}={aVar} : {aType}()
#[condition][]And=and

#Person
[condition][]There is a Person {p} with name of "{name}"={p} : Person(name=="{name}")
[condition][]Person is not yet {age} years old=Person(age < {age})
[consequence][]Log "{message}"=System.out.println("{message}");

[condition][]Person is at least {age} years old=Person(age >= {age})
[condition][] - assign {aField} to {aVar}={aVar} : {aField}

#Article
#[condition  ][]There is an? {aType} {aVar}={aVar} : {aType}()
#[condition  ][]- assign {aField} to {aVar}={aVar} : {aField}
#[condition  ][]- with {aField} {aOp} {aVal}={aField} {aOp} {aVal}
#consequence][]Set the price of {fVar} to {aVal}=modify( {fVar} )\{ setPrice( {aVal} ) \}