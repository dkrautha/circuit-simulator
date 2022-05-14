package edu.stevens.circuit.simulator;

import java.util.List;

/**
 * Represents something that accepts inputs, perfroms transformations, and yields outputs.
 */
public interface Logic {
        /**
         * Assigns the given signals to the input wires of the thing.
         * 
         * For gates, the inputs wires themselves should be assigned these new values. For a
         * circuit, these wire will be attached to Contact points.
         * 
         * @param inSignals
         * @throws InvalidLogicParametersException
         */
        public void feed(List<Signal> inSignals) throws InvalidLogicParametersException;

        /**
         * Performs the same actions as feed(), but accepts a String as input.
         * 
         * @param inSignals
         * @throws InvalidLogicParametersException
         * @throws MalformedSignalException
         */
        public void feedFromString(String inSignals)
                        throws InvalidLogicParametersException, MalformedSignalException;

        /**
         * Lets all inner components perform their logic and generate outputs.
         * 
         * A single call to this function should cause the entire circuit to stabilize.
         * 
         * @return If any wire's signals were changed, returns true;
         */
        public boolean propagate();

        /**
         * Read the signal values on the output wires.
         * 
         * @return Output wires values as a list.
         */
        public List<Signal> read();

        /**
         * Performs the combination of feeding, propagating, and reading.
         * 
         * TODO turns out you can give default impls in interfaces, maybe add one here?
         * 
         * @param inputs
         * @return Output wires values as a list, after logic have been performed.
         * @throws InvalidLogicParametersException
         */
        public default List<Signal> inspect(List<Signal> inputs)
                        throws InvalidLogicParametersException {
                feed(inputs);
                propagate();
                return read();
        }

        /**
         * Performs the same actions as inspect(), but accepts a String as input.
         * 
         * @param inputs
         * @return
         * @throws InvalidLogicParametersException
         * @throws MalformedSignalException
         */
        public default String inspectFromString(String inputs)
                        throws InvalidLogicParametersException, MalformedSignalException {
                feedFromString(inputs);
                propagate();
                return Signal.toString(read());
        }
}
