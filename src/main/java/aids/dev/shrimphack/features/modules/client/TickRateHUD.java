package aids.dev.shrimphack.features.modules.client;

import aids.dev.shrimphack.event.impl.Render2DEvent;
import aids.dev.shrimphack.features.modules.Module;
import aids.dev.shrimphack.features.settings.Setting;

import java.util.Random;

public class TickRateHUD extends Module {
    private final Setting<Integer> yCoords = new Setting<>("Ycoords", 2, 0, 485);
    private final Setting<Integer> xCoords = new Setting<>("Xcoords", 2, 0, 710);
    public TickRateHUD() {
        super("TickRateHUD", "", Category.CLIENT, true, false, false);
    }
    private final Random random = new Random();
    private double lastTickRate = 20.0;
    private double lastChange = 0.0;

    public void render(int color, Render2DEvent event) {
            double[] tickRates = getTickRates();
            String tickRateString = formatTickRates(tickRates);

            event.getContext().drawTextWithShadow(
                    mc.textRenderer,
                    tickRateString,
                    xCoords.getPlannedValue(),
                    yCoords.getPlannedValue(),
                    color
            );
        }
    }

    private String formatTickRates(double[] tickRates) {
        StringBuilder tickRateString = new StringBuilder("Tick-rate: ");

        for (int i = 0; i < tickRates.length; i++) {
            int percentage = 100 - (i * 25);
            tickRateString.append(String.format("%.2f (%d)", tickRates[i], percentage));
            if (i < tickRates.length - 1) {
                tickRateString.append(". ");
            }
        }
        tickRateString.append(". ").append(String.format("%.1fs", lastChange));

        return tickRateString.toString();
    }

    private double[] getTickRates() {
        double[] tickRates = new double[4];

        // Get the current TPS (simulated)
        double currentTickRate = 20.0 - (random.nextDouble() * 0.5);

        // Predict the next two TPS values
        double predictedTickRate1 = currentTickRate - (random.nextDouble() * 0.1);
        double predictedTickRate2 = predictedTickRate1 - (random.nextDouble() * 0.1);

        tickRates[0] = currentTickRate;
        tickRates[1] = predictedTickRate1;
        tickRates[2] = predictedTickRate2;
        tickRates[3] = currentTickRate; // Placeholder for the last value

        // Calculate the change in TPS
        lastChange = Math.abs(currentTickRate - lastTickRate);
        lastTickRate = currentTickRate;

        return tickRates;
    }
}
