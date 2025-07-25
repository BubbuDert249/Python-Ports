import javax.microedition.midlet.*;
import javax.microedition.lcdui.*;
import java.util.Hashtable;
import java.util.Vector;

public class JavaPy extends MIDlet implements CommandListener {

    private Display display;
    private TextBox codeBox;
    private Command runCommand;
    private Form form;
    private StringItem output;
    private Command backCommand;

    public void startApp() {
        display = Display.getDisplay(this);
        codeBox = new TextBox("JavaPy - Python Code", "", 2048, TextField.ANY);
        runCommand = new Command("Run", Command.OK, 1);
        codeBox.addCommand(runCommand);
        codeBox.setCommandListener(this);
        display.setCurrent(codeBox);
    }

    public void pauseApp() {}
    public void destroyApp(boolean unconditional) {}

    public void commandAction(Command c, Displayable d) {
        if (c == runCommand) {
            String result = interpret(codeBox.getString());
            showOutput(result);
        }
    }

    private void showOutput(String result) {
        form = new Form("Output");
        output = new StringItem("", result);
        backCommand = new Command("Back", Command.BACK, 1);
        form.append(output);
        form.addCommand(backCommand);
        form.setCommandListener(new CommandListener() {
            public void commandAction(Command c, Displayable d) {
                display.setCurrent(codeBox);
            }
        });
        display.setCurrent(form);
    }

    private String interpret(String code) {
        StringBuffer out = new StringBuffer();
        Hashtable vars = new Hashtable();
        String[] lines = splitLines(code);
        boolean skip = false;
        int i = 0;
        int indent = 0;
        boolean inIf = false;
        boolean lastCondition = false;
        boolean executing = true;
        int safety = 0;

        while (i < lines.length && safety < 1000) {
            safety++;
            String line = lines[i].trim();
            if (line.equals("") || line.startsWith("#") || line.startsWith("import ")) {
                i++; continue;
            }

            // if/elif/else handling
            if (line.startsWith("if ") && line.endsWith(":")) {
                String cond = line.substring(3, line.length() - 1);
                lastCondition = evalCondition(cond, vars);
                executing = lastCondition;
                inIf = true;
                i++; continue;
            } else if (line.startsWith("elif ") && line.endsWith(":")) {
                if (!inIf) { i++; continue; }
                if (lastCondition) {
                    executing = false;
                } else {
                    String cond = line.substring(5, line.length() - 1);
                    lastCondition = evalCondition(cond, vars);
                    executing = lastCondition;
                }
                i++; continue;
            } else if (line.equals("else:")) {
                if (!inIf) { i++; continue; }
                executing = !lastCondition;
                i++; continue;
            }

            // while loop
            if (line.startsWith("while ") && line.endsWith(":")) {
                String cond = line.substring(6, line.length() - 1);
                int loopStart = i + 1;
                Vector body = new Vector();
                while (loopStart < lines.length && lines[loopStart].startsWith("    ")) {
                    body.addElement(lines[loopStart].substring(4));
                    loopStart++;
                }
                int loopCount = 0;
                while (evalCondition(cond, vars) && loopCount < 100) {
                    for (int j = 0; j < body.size(); j++) {
                        String bline = ((String) body.elementAt(j)).trim();
                        if (!bline.equals("")) {
                            evalLine(bline, vars, out);
                        }
                    }
                    loopCount++;
                }
                i = loopStart;
                continue;
            }

            if (executing) {
                evalLine(line, vars, out);
            }

            if (!line.startsWith("elif") && !line.startsWith("else")) {
                inIf = false;
            }

            i++;
        }
        return out.toString();
    }

    private void evalLine(String line, Hashtable vars, StringBuffer out) {
        try {
            if (line.startsWith("print(") && line.endsWith(")")) {
                String expr = line.substring(6, line.length() - 1);
                out.append(resolve(expr, vars)).append("\n");
            } else if (line.startsWith("time.sleep(") && line.endsWith(")")) {
                String arg = line.substring(11, line.length() - 1);
                int ms = (int)(Double.parseDouble(arg) * 1000);
                Thread.sleep(ms);
            } else if (line.indexOf("=") != -1 && !line.startsWith("if ") && !line.startsWith("elif ")) {
                String[] parts = splitByEquals(line);
                String key = parts[0].trim();
                String value = parts[1].trim();

                if (value.startsWith("\"") || value.startsWith("'")) {
                    vars.put(key, stripQuotes(value));
                } else if (value.equals("True") || value.equals("False")) {
                    vars.put(key, value.equals("True") ? "1" : "0");
                } else if (value.startsWith("str(") && value.endsWith(")")) {
                    String arg = value.substring(4, value.length() - 1);
                    vars.put(key, resolve(arg, vars));
                } else if (value.startsWith("len(") && value.endsWith(")")) {
                    String arg = resolve(value.substring(4, value.length() - 1), vars);
                    vars.put(key, String.valueOf(arg.length()));
                } else {
                    vars.put(key, resolve(value, vars));
                }
            } else if (line.indexOf("+=") != -1 || line.indexOf("-=") != -1 || line.indexOf("*=") != -1 || line.indexOf("/=") != -1) {
                String op = line.indexOf("+=") != -1 ? "+=" :
                            line.indexOf("-=") != -1 ? "-=" :
                            line.indexOf("*=") != -1 ? "*=" : "/=";
                String[] parts = splitByOp(line, op);
                String var = parts[0].trim();
                String val = resolve(parts[1].trim(), vars);
                String oldVal = (String) vars.get(var);
                double result = applyMath(Double.parseDouble(oldVal), Double.parseDouble(val), op);
                vars.put(var, String.valueOf(result));
            }
        } catch (Exception e) {
            out.append("[ERROR] ").append(e.toString()).append("\n");
        }
    }

    private boolean evalCondition(String cond, Hashtable vars) {
        try {
            cond = cond.trim();
            String[] ops = {"==", "!=", "<=", ">=", "<", ">"};
            for (int i = 0; i < ops.length; i++) {
                if (cond.indexOf(ops[i]) != -1) {
                    String[] parts = splitByOp(cond, ops[i]);
                    String left = resolve(parts[0].trim(), vars);
                    String right = resolve(parts[1].trim(), vars);
                    double l = Double.parseDouble(left);
                    double r = Double.parseDouble(right);
                    if (ops[i].equals("==")) return l == r;
                    if (ops[i].equals("!=")) return l != r;
                    if (ops[i].equals("<=")) return l <= r;
                    if (ops[i].equals(">=")) return l >= r;
                    if (ops[i].equals("<")) return l < r;
                    if (ops[i].equals(">")) return l > r;
                }
            }
        } catch (Exception e) {}
        return false;
    }

    private String resolve(String expr, Hashtable vars) {
        expr = expr.trim();
        if ((expr.startsWith("\"") && expr.endsWith("\"")) || (expr.startsWith("'") && expr.endsWith("'"))) {
            return stripQuotes(expr);
        }
        if (vars.containsKey(expr)) return (String) vars.get(expr);
        return expr;
    }

    private String stripQuotes(String s) {
        if ((s.startsWith("\"") && s.endsWith("\"")) || (s.startsWith("'") && s.endsWith("'"))) {
            return s.substring(1, s.length() - 1);
        }
        return s;
    }

    private String[] splitLines(String text) {
        Vector v = new Vector();
        int p = 0;
        int i;
        while ((i = text.indexOf('\n', p)) >= 0) {
            v.addElement(text.substring(p, i));
            p = i + 1;
        }
        v.addElement(text.substring(p));
        String[] arr = new String[v.size()];
        v.copyInto(arr);
        return arr;
    }

    private String[] splitByEquals(String line) {
        int idx = line.indexOf('=');
        String left = line.substring(0, idx);
        String right = line.substring(idx + 1);
        return new String[]{left, right};
    }

    private String[] splitByOp(String line, String op) {
        int idx = line.indexOf(op);
        String left = line.substring(0, idx);
        String right = line.substring(idx + op.length());
        return new String[]{left, right};
    }

    private double applyMath(double a, double b, String op) {
        if (op.equals("+=")) return a + b;
        if (op.equals("-=")) return a - b;
        if (op.equals("*=")) return a * b;
        if (op.equals("/=")) return a / b;
        return 0;
    }
}
