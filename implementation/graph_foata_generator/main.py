import regex as re
import graphviz


def main(input_file_path):
    # get data
    operations, trace, alphabet  = parse_input(input_file_path)

    # solve
    dr = get_dependency_relation(operations)
    ir = get_independent_relation(operations)
    fnf = get_foata_normal_form(operations, trace)
    graph = create_dependency_graph_matrix(operations, trace)
    graph_dot = convert_graph_to_dot(trace, graph)
    
    # print result
    print(f"D: {dr}\n\nI: {ir}\n\nFNF: {fnf}\n\nGraph (dot): {graph_dot}\n\n")

    # save result to file
    save_result(dr, ir, fnf, graph_dot)


def parse_input(input_path):
    """
    parser requires specific file structure like below (without blank
    lines at the top and bottom):

    (a) x := x + 1
    (b) y := y + 2z
    (c) x := 3x + z
    (d) w := w + v
    (e) z := y − z
    (f) v := x + v

    A = {a, b, c, d, e, f}

    w = acdcfbbe
    
    """
    
    parsed_input = {}
    
    with open(input_path) as input_file:
        raw_input = input_file.read().split(sep="\n")
        
        for row in raw_input[:-4]:
            op_id = row.split(sep=')')[0][1:]
            save_to = row.split(sep=" ")[1]
            read_from = [row.split(sep=" ")[3], row.split(sep=" ")[5]]
            parsed_input.setdefault(op_id, [save_to, read_from])

    alphabet = re.findall('[a-zA-Z][0-9][0-9]', raw_input[-3])
    print(alphabet)
    return parsed_input, raw_input[-1][4:].split(sep=" "), alphabet


def get_dependency_relation(operations):
    """
    algorithm:
    for all pairs of operations check if one of them saves to a variable from
    which the other operation reads and vice versa - if at least one of this 
    conditions is fulfilled, the pair of operations is added to the dependecy
    relation (dr)
    """
    
    dr = []

    for op_id1, op1 in operations.items():
        save_to1 = op1[0]
        read_from1 = op1[1]
        
        for op_id2, op2 in operations.items():
            save_to2 = op2[0]
            read_from2 = op2[1]
            
            if save_to1 in read_from2 or save_to2 in read_from1:
                dr.append((op_id1, op_id2))

    # set for uniqueness
    return  sorted(list(set(dr)))


def get_independent_relation(operations):
    """
    algorithm:
    create all possible pairs of operations and remove those that are in dependency relation
    """
    
    dr = get_dependency_relation(operations)
    pairs = [(op_id1, op_id2) for op_id1 in operations.keys() for op_id2 in operations.keys()]
    
    return sorted([pair for pair in pairs if pair not in dr])
    

def get_foata_normal_form(operations, trace):
    """
    algorithm:
    https://link.springer.com/content/pdf/10.1007/978-3-642-59126-6_8.pdf
    page 10
    """
    
    ir = get_independent_relation(operations)
    alphabet = list(operations.keys())
    
    # create stacks
    stacks = {op_id: [] for op_id in alphabet}
    for processed_op_id in trace[::-1]:
        for op_id in alphabet:
            if processed_op_id == op_id:
                stacks[op_id].append(processed_op_id)
            elif (op_id, processed_op_id) not in ir:
                stacks[op_id].append('*')
          
    # pop from stacks - create FNF       
    fnf = []
    while count_empty_stacks(stacks) < len(stacks):
        fnf_seg = []        
        tops = list(map(lambda stack: stack[-1], filter(lambda stack: len(stack) > 0 and stack[-1] != '*', stacks.values())))
        
        for top in tops:
                stacks[top].pop()
                fnf_seg.append(top)
                            
                for op_id in alphabet:
                    if op_id != top and (op_id, top) not in ir:
                        stacks[op_id].pop()                                  
                    
        if fnf_seg != "":
            fnf.append(sorted(fnf_seg))
    
    # string formatting    
    return "".join(map(lambda fnf_seg: f"({''.join(fnf_seg)})", fnf))


def count_empty_stacks(stacks):
    return sum([1 if len(stack) == 0 else 0 for stack in stacks.values()])


# creates dependency graph in a form of a matrix
def create_dependency_graph_matrix(operations, trace):
    n = len(trace)
    dr = get_dependency_relation(operations)
    
    graph_matrix = [[0 for _ in range(n)] for _ in range(n)]
    for i in range(n):
        for j in range(i + 1, n):
            if (trace[i], trace[j]) in dr:
                graph_matrix[i][j] = 1 
    
    return graph_matrix


# simplifying the graph - remove transitive edges from graph in a form of a matrix
def remove_transitive_edges(trace, graph_matrix):
    n = len(trace)
    
    for i in range(n):
        for j in range(n):
            if graph_matrix[i][j]:
                for k in range(n):
                    if graph_matrix[j][k]:
                        graph_matrix[i][k] = 0


def convert_graph_to_dot(trace, graph_matrix):
    n = len(trace)
    graph_dot = graphviz.Digraph("graph")
    remove_transitive_edges(trace, graph_matrix)
    
    for i, ch in enumerate(trace):
        graph_dot.node(name=str(i), label=ch)
        
    for i in range(n):
        for j in range(n):
            if graph_matrix[i][j]:
                graph_dot.edge(str(i), str(j))
    
    return graph_dot


def save_result(dr, ir, fnf, graph_dot):
    path = 'output'
    
    with open(path + "/result.txt", 'w+') as output_file:
        output_file.write(f"D: {dr}\n\nI: {ir}\n\nFNF: {fnf}\n\nGraph (dot): {graph_dot}")


if __name__ == "__main__":
    main("./inputs/input.txt")
