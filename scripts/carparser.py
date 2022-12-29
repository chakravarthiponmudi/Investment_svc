import casparser

# Get data in json format
json_str = casparser.read_cas_pdf("/home/chakra/Downloads/copy.pdf","sairam123*" , output="json")

print(json_str)
