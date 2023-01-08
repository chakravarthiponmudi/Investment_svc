import casparser

# Get data in json format
json_str = casparser.read_cas_pdf("/Users/cponmudi/playground/investment/investment/src/test/java/com/chakra/projects/investment/21-22_cams.pdf","sairam123*" , output="json")

print(json_str)
