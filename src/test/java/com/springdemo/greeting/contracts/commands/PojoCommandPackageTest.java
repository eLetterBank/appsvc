package com.springdemo.greeting.contracts.commands;

import com.openpojo.reflection.PojoClass;
import com.openpojo.reflection.impl.PojoClassFactory;
import com.openpojo.validation.Validator;
import com.openpojo.validation.ValidatorBuilder;
import com.openpojo.validation.rule.impl.*;
import com.openpojo.validation.test.impl.DefaultValuesNullTester;
import com.openpojo.validation.test.impl.GetterTester;
import com.openpojo.validation.test.impl.SetterTester;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

public class PojoCommandPackageTest {
    // Configured for expectation, so we know when a class gets added or removed.
    private static final int EXPECTED_CLASS_COUNT = 3;

    // The package to test
    private static final String POJO_PACKAGE = "com.springdemo.greeting.contracts.commands";

    private List<PojoClass> pojoClasses;
    private Validator validator;

    @Before
    public void setup() {
        pojoClasses = PojoClassFactory.getPojoClasses(POJO_PACKAGE, null);
        validator = ValidatorBuilder.create()
                /****
                 * Create Rules to validate structure of the classes
                 ****/
                // #TODO: Make sure we have a getter and setter
                //.with(new GetterMustExistRule())
                //.with(new SetterMustExistRule())

                // We don't want any primitives in our Pojos.
                //.with(new NoPrimitivesRule())

                // Pojo's should stay simple, don't allow nested classes, anonymous or declared.
                //.with(new NoNestedClassRule())

                // Static fields must be final
                .with(new NoStaticExceptFinalRule())

                // Serializable must have serialVersionUID
                .with(new SerializableMustHaveSerialVersionUIDRule())

                // Don't shadow parent's field names.
                .with(new NoFieldShadowingRule())

                // What about public fields, use one of the following rules
                // allow them only if they are static and final.
                .with(new NoPublicFieldsExceptStaticFinalRule())

                // Or you can be more restrictive and not allow ANY public fields in a Pojo.
                // pojoValidator.addRule(new NoPublicFieldsRule());

                // Finally, what if you are testing your Testing code?
                // Make sure your tests are properly named
                .with(new TestClassMustBeProperlyNamedRule())

                /****
                 * Create Testers to validate the behavior of the classes at runtime.
                 ****/
                // Make sure our setters and getters are behaving as expected.
                .with(new SetterTester())
                .with(new GetterTester())

                // We don't want any default values to any fields - unless they are declared final or are primitive.
                .with(new DefaultValuesNullTester())

                /**
                 * finalize Validator building.
                 */
                .build();
    }

    @Test
    public void ensureExpectedPojoCount() {
        Assert.assertEquals("Classes added / removed?", EXPECTED_CLASS_COUNT, pojoClasses.size());
    }

    @Test
    public void testPojoStructureAndBehavior() {
        validator.validate(pojoClasses);
    }
}