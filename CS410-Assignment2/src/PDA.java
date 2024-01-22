    import java.io.BufferedWriter;
    import java.io.FileWriter;
    import java.io.IOException;
    import java.util.ArrayList;
    import java.util.Objects;
    import java.util.Stack;

    public class PDA {
        private int numberOfInputAlphabet;
        private int numberOfStackAlphabet;
        private int numberOfGoalStates;
        private int numberOfStates;
        private ArrayList<String> statesList;
        private ArrayList<String> startState;
        private ArrayList<String> finalStates;
        private ArrayList<String> stackAlphabet;
        private ArrayList<String> initialStackSymbol;
        private ArrayList<String> inputAlphabet;
        private ArrayList<String> transitionLines;
        boolean operable = true;

        public PDA(int numberOfInputAlphabet, int numberOfStackAlphabet,
                   int numberOfGoalStates, int numberOfStates,
                   ArrayList<String> statesList, ArrayList<String> startState,
                   ArrayList<String> finalStates, ArrayList<String> stackAlphabet,
                   ArrayList<String> initialStackSymbol, ArrayList<String> inputAlphabet,
                   ArrayList<String> transitionLines) {
            this.numberOfInputAlphabet = numberOfInputAlphabet;
            this.numberOfStackAlphabet = numberOfStackAlphabet;
            this.numberOfGoalStates = numberOfGoalStates;
            this.numberOfStates = numberOfStates;
            this.statesList = statesList;
            this.startState = startState;
            this.finalStates = finalStates;
            this.stackAlphabet = stackAlphabet;
            this.initialStackSymbol = initialStackSymbol;
            this.inputAlphabet = inputAlphabet;
            this.transitionLines = transitionLines;
        }

        public void simulatePDA(ArrayList<String> inputLines, String outputFileName) {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(outputFileName))) {
                for (String inputLine : inputLines) {
                    Stack<String> stack = new Stack<>();
                    ArrayList<String> currentState = new ArrayList<>(startState);
                    StringBuilder route = new StringBuilder("Route taken: ");
                    boolean accepted = processInput(inputLine, stack, currentState, route);

                    writer.write(route.toString());
                    writer.newLine();

                    System.out.println("Final State: " + currentState.get(0));
                    System.out.println("Stack: " + stack);
                    System.out.println(route);

                    if (accepted) {
                        writer.write("Accepted");
                        System.out.println("String "+ inputLine + " accepted");
                    } else {
                        writer.write("Rejected");
                        System.out.println("String "+ inputLine + " rejected");
                    }
                    writer.newLine();

                    System.out.println();
                    operable = true;
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        private boolean processInput(String inputLine, Stack<String> stack, ArrayList<String> currentState, StringBuilder route) {
            while (this.operable) {
                for (char inputSymbol : inputLine.toCharArray()) {
                    boolean validTransition = false;
                    for (String transition : transitionLines) {
                        String[] parts = transition.split(" ");
                        if ((currentState.get(0).equals(parts[0]) && parts[1].equals("e") && operable == true)) {
                            performTransition(transition, stack);
                            route.append(currentState.get(0)).append(" ");
                            currentState.set(0, getNextState(transition));
                            //System.out.println("getNext " + currentState.set(0, getNextState(transition)));
                            validTransition = true;
                        } else if ((currentState.get(0).equals(parts[0])) && (inputSymbol == parts[1].charAt(0)) && operable == true) {
                            //System.out.println("returned transition: " + transition);
                            performTransition(transition, stack);
                            route.append(currentState.get(0)).append(" ");
                            currentState.set(0, getNextState(transition));
                            //System.out.println("getNext " + currentState.set(0, getNextState(transition)));
                            validTransition = true;
                            break;
                        }
                        //System.out.println(operable);
                    }
                    if (!validTransition) {
                        System.out.println("No valid transition found");
                        return false;
                    }
                }

                for (String transition : transitionLines) {
                    String[] parts = transition.split(" ");
                    if (operable) {
                        if ((currentState.get(0).equals(parts[0]) && parts[1].equals("e"))) {
                            performTransition(transition, stack);
                            //System.out.println("slm ya");
                            route.append(currentState.get(0)).append(" ");
                            currentState.set(0, getNextState(transition));
                            //System.out.println("getNext " + currentState.set(0, getNextState(transition)));
                        }
                    }
                }
                if (operable) {
                    route.append(currentState.get(0)).append(" ");
                }
                this.operable = false;
            }
            //System.out.println("? " + operable);

            return finalStates.contains(currentState.get(0));
        }


        private String getNextState(String transition) {
            String[] parts = transition.split(" ");
            return parts[4];
        }

        private void performTransition(String transition, Stack<String> stack) {
            String[] parts = transition.split(" ");
            if (!parts[3].equals("e")) {

                for (int i = parts[3].length() - 1; i >= 0; i--) {
                    stack.push(String.valueOf(parts[3].charAt(i)));
                }
            }

            String topSymbol = stack.peek();
            //System.out.println("Top : " + topSymbol + "  Parts[2]: " + parts[2]);
            if(!(stack.isEmpty()) && (!parts[2].equals("e")) && (topSymbol.equals(parts[2]))){
                stack.pop();
                operable=true;
            }else if(!(stack.isEmpty()) &&parts[2].equals("e")){
                stack=stack;
            }else if((stack.isEmpty()) && (!parts[2].equals("e"))) {
                operable=false;
            }
            else if((!(topSymbol.equals(parts[2])))){
                operable =false;
            }
            System.out.println("Transition performed: " + transition);
            System.out.println("Stack is:  " + stack);
        }
    }



