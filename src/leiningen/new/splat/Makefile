# This Makefile is here to ensure that all of the classpath
# directories exist before Java runs - if a classpath entry doesn't
# exist before Java runs, it doesn't find any resources in that entry
# even if it is subsequently created.

.PHONY: dev

%/.ph: 
	@mkdir -p $(@D)
	@touch $@

target/.ph: src/cljx/.ph target/generated/clj/.ph target/generated/cljs/.ph target/resources/.ph
	@mkdir -p $(@D)
	@touch $@

dev: target/.ph
	lein dev
