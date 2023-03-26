import casparser

# Get data in json format
json_str = casparser.read_cas_pdf("/home/chakra/Playground/Investments/investment/upload-dir/complete2.pdf","test123" , output="json")

print(json_str)
