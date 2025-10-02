package EstimadoresProbabilistica;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import Comun.Amostra;

public class ClassificadorBayesiano {

    private boolean usarKDE;
    private HashMap<Integer, Double> proporcaoClasse;
    private HashMap<Integer, List<List<Double>>> valoresPorClasse;

    public ClassificadorBayesiano(boolean usarKDE) {
        this.usarKDE = usarKDE;
        proporcaoClasse = new HashMap<>();
        valoresPorClasse = new HashMap<>();
    }

    public void treinar(List<Amostra> treino) {
        int total = treino.size();

        // Separar amostras por classe e por feature
        for (Amostra a : treino) {
            int classe = (int) a.Y[0];
            proporcaoClasse.put(classe, proporcaoClasse.getOrDefault(classe, 0.0) + 1);

            valoresPorClasse.putIfAbsent(classe, new ArrayList<>());
            while (valoresPorClasse.get(classe).size() < a.X.length) {
                valoresPorClasse.get(classe).add(new ArrayList<>());
            }

            for (int j = 0; j < a.X.length; j++) {
                valoresPorClasse.get(classe).get(j).add(a.X[j]);
            }
        }

        // Calcular proporções P(C)
        for (Integer classe : proporcaoClasse.keySet()) {
            proporcaoClasse.put(classe, proporcaoClasse.get(classe) / total);
        }
    }

    public int classificar(Amostra a) {
        HashMap<Integer, Double> probClasse = new HashMap<>();

        for (Integer classe : proporcaoClasse.keySet()) {
            double prob = proporcaoClasse.get(classe);

            for (int j = 0; j < a.X.length; j++) {
                List<Double> valores = valoresPorClasse.get(classe).get(j);

                if (usarKDE) {
                    prob *= GaussianaKDE.distribDensidadeProbKde(a.X[j], valores);
                } else {
                    prob *= GaussianaKDE.distribDensidadeProb(a.X[j], valores);
                }
            }
            probClasse.put(classe, prob);
        }

        return probClasse.entrySet().stream()
                .max((e1, e2) -> Double.compare(e1.getValue(), e2.getValue()))
                .get().getKey();
    }

    public double avaliar(List<Amostra> base) {
        int erros = 0;
        for (Amostra a : base) {
            int previsto = classificar(a);
            if (previsto != (int) a.Y[0]) erros++;
        }
        return 100.0 * (base.size() - erros) / base.size();
    }
}
