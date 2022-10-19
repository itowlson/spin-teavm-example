package wit_random_thing;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

import org.teavm.interop.Memory;
import org.teavm.interop.Address;
import org.teavm.interop.Import;
import org.teavm.interop.Export;

public final class RandomThing {
    private RandomThing() {}
    
    public static enum AnimalType {
        CAT, DOG
    }
    
    public static final class Request {
        public final byte tag;
        private final Object value;
        
        private Request(byte tag, Object value) {
            this.tag = tag;
            this.value = value;
        }
        
        public static Request joke() {
            return new Request(JOKE, null);
        }
        
        public static Request animalFact(AnimalType animalFact) {
            return new Request(ANIMAL_FACT, animalFact);
        }
        
        public AnimalType getAnimalFact() {
            if (this.tag == ANIMAL_FACT) {
                return (AnimalType) this.value;
            } else {
                throw new RuntimeException("expected ANIMAL_FACT, got " + this.tag);
            }
        }
        
        public static final byte JOKE = 0;
        public static final byte ANIMAL_FACT = 1;
    }
    
    public static final class Error {
        public final byte tag;
        private final Object value;
        
        private Error(byte tag, Object value) {
            this.tag = tag;
            this.value = value;
        }
        
        public static Error network(String network) {
            return new Error(NETWORK, network);
        }
        
        public static Error service(short service) {
            return new Error(SERVICE, service);
        }
        
        public static Error response() {
            return new Error(RESPONSE, null);
        }
        
        public String getNetwork() {
            if (this.tag == NETWORK) {
                return (String) this.value;
            } else {
                throw new RuntimeException("expected NETWORK, got " + this.tag);
            }
        }
        
        public short getService() {
            if (this.tag == SERVICE) {
                return (short) this.value;
            } else {
                throw new RuntimeException("expected SERVICE, got " + this.tag);
            }
        }
        
        public static final byte NETWORK = 0;
        public static final byte SERVICE = 1;
        public static final byte RESPONSE = 2;
    }
    
    @Export(name = "get-random-thing")
    private static int wasmExportGetRandomThing(int p0, int p1) {
        
        Request lifted;
        
        switch (p0) {
            case 0: {
                
                lifted = Request.joke();
                break;
            }
            case 1: {
                
                lifted = Request.animalFact(AnimalType.values()[p1]);
                break;
            }
            
            default: throw new AssertionError("invalid discriminant: " + (p0));
        }
        
        Result<String, Error> result = RandomThingImpl.getRandomThing(lifted);
        
        switch ((result).tag) {
            case 0: {
                String payload = (String) (result).value;
                Address.fromInt((RETURN_AREA) + 0).putByte((byte) (0));
                byte[] bytes = (payload).getBytes(StandardCharsets.UTF_8);
                
                Address address = Memory.malloc(bytes.length, 1);
                Memory.putBytes(address, bytes, 0, bytes.length);
                Address.fromInt((RETURN_AREA) + 8).putInt(bytes.length);
                Address.fromInt((RETURN_AREA) + 4).putInt(address.toInt());
                
                break;
            }
            case 1: {
                Error payload6 = (Error) (result).value;
                Address.fromInt((RETURN_AREA) + 0).putByte((byte) (1));
                
                switch ((payload6).tag) {
                    case 0: {
                        String payload9 = (String) (payload6).value;
                        Address.fromInt((RETURN_AREA) + 4).putByte((byte) (0));
                        byte[] bytes10 = (payload9).getBytes(StandardCharsets.UTF_8);
                        
                        Address address11 = Memory.malloc(bytes10.length, 1);
                        Memory.putBytes(address11, bytes10, 0, bytes10.length);
                        Address.fromInt((RETURN_AREA) + 12).putInt(bytes10.length);
                        Address.fromInt((RETURN_AREA) + 8).putInt(address11.toInt());
                        
                        break;
                    }
                    case 1: {
                        short payload14 = (short) (payload6).value;
                        Address.fromInt((RETURN_AREA) + 4).putByte((byte) (1));
                        Address.fromInt((RETURN_AREA) + 8).putShort((short) (((int) (payload14)) & 0xFFFF));
                        
                        break;
                    }
                    case 2: {
                        
                        Address.fromInt((RETURN_AREA) + 4).putByte((byte) (2));
                        
                        break;
                    }
                    
                    default: throw new AssertionError("invalid discriminant: " + (payload6).tag);
                }
                
                break;
            }
            
            default: throw new AssertionError("invalid discriminant: " + (result).tag);
        }
        return RETURN_AREA;
        
    }
    
    @Export(name = "cabi_post_get-random-thing")
    private static void wasmExportGetRandomThingPostReturn(int p0) {
        
        switch ((((int) Address.fromInt((p0) + 0).getByte()) & 0xFF)) {
            case 0: {
                Memory.free(Address.fromInt(Address.fromInt((p0) + 4).getInt()), Address.fromInt((p0) + 8).getInt(), 1);
                
                break;
            }
            case 1: {
                
                switch ((((int) Address.fromInt((p0) + 4).getByte()) & 0xFF)) {
                    case 0: {
                        Memory.free(Address.fromInt(Address.fromInt((p0) + 8).getInt()), Address.fromInt((p0) + 12).getInt(), 1);
                        
                        break;
                    }
                    case 1: {
                        
                        break;
                    }
                    case 2: {
                        
                        break;
                    }
                }
                
                break;
            }
        }
        
    }
    
    public static final class Result<Ok, Err> {
        public final byte tag;
        private final Object value;
        
        private Result(byte tag, Object value) {
            this.tag = tag;
            this.value = value;
        }
        
        public static <Ok, Err> Result<Ok, Err> ok(Ok ok) {
            return new Result<>(OK, ok);
        }
        
        public static <Ok, Err> Result<Ok, Err> err(Err err) {
            return new Result<>(ERR, err);
        }
        
        public Ok getOk() {
            if (this.tag == OK) {
                return (Ok) this.value;
            } else {
                throw new RuntimeException("expected OK, got " + this.tag);
            }
        }
        
        public Err getErr() {
            if (this.tag == ERR) {
                return (Err) this.value;
            } else {
                throw new RuntimeException("expected ERR, got " + this.tag);
            }
        }
        
        public static final byte OK = 0;
        public static final byte ERR = 1;
    }
    private static final int RETURN_AREA = Memory.malloc(16, 4).toInt();
}
